/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.enemy.Enemy;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.projectile.EnemyProjectile;
import dk.sdu.mmmi.cbse.projectile.PlayerProjectile;
import dk.sdu.mmmi.cbse.projectile.Projectile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Agger
 */
public class AsteroidCollisionSystem implements IPostEntityProcessingService {

    /**
     * Asteroid should be split up into 2 smaller Asteroids if hit by
     * EnemyProjectile or PlayerProjectile. Asteroid should be removed if hit by
     * an Enemy or Player.
     *
     * @param gameData
     * @param world
     */
    @Override
    public void process(GameData gameData, World world) {
        List<Entity> asteroids = new ArrayList<>(world.getEntities(Asteroid.class));
        List<Entity> foreignObjects = new ArrayList<>();
        foreignObjects.addAll(world.getEntities(Player.class));
        foreignObjects.addAll(world.getEntities(Enemy.class));
        foreignObjects.addAll(world.getEntities(EnemyProjectile.class));
        foreignObjects.addAll(world.getEntities(PlayerProjectile.class));

        for (Entity a : asteroids) {
            Asteroid as = (Asteroid) a;
            // Check collision for each foreign Object we want something to happen with on collision
            for (Entity obj : foreignObjects) {
                this.checkCollision(world, as, obj);
            }
        }
    }

    private void checkCollision(World world, Entity asteroid, Entity foreign) {
        Asteroid as = (Asteroid) asteroid;
        Player pla = null;
        Enemy ene = null;
        Projectile pr = null;
        if (foreign instanceof Player) {
            pla = (Player) foreign;
        } else if (foreign instanceof Enemy) {
            ene = (Enemy) foreign;
        } else if ((foreign instanceof EnemyProjectile) || (foreign instanceof PlayerProjectile)) {
            pr = (Projectile) foreign;
        }

        // Get Asteroid Position part
        PositionPart asPos = as.getPart(PositionPart.class);

        // Asteroid collision check with Player
        if (pla != null) {
            // Get Player Position Part
            PositionPart plaPos = pla.getPart(PositionPart.class);
            // Use the distance formula to determine the distance between the player and the asteroid
            // There is a few errors with this way of handling collision, the result from the pythagoras can be 0, which means there must be a faulty collision detected somewhere.
            // We can work around this by casting to (int), which seems to produce a more reliable collision calculation
            double pythagoras = Math.pow((int) asPos.getX() - (int) plaPos.getX(), 2) + Math.pow((int) asPos.getY() - (int) plaPos.getY(), 2);
            // Check for faulty collisions
            if ((int) pythagoras == 0 || ((int) plaPos.getX() == (int) asPos.getY() && (int) plaPos.getY() == (int) asPos.getY())) {
                System.out.println("Faulty collision detected.");
                return;
            }
            // We then make use of the distance formula
            if (Math.sqrt((int) pythagoras) < pla.getRadius() + as.getRadius()) {
                System.out.println("Collision Detected with asteroid and player");
                world.removeEntity(as);
                LifePart plaLp = pla.getPart(LifePart.class);
                if (plaLp.isIsHit()) {
                    world.removeEntity(pla);
                    return;
                }
                plaLp.setIsHit(true);
            }
        }
        // Asteroid collision check with Enemy
        if (ene != null) {
            // Get Enemy Position Part
            PositionPart enePos = ene.getPart(PositionPart.class);
            // Check enemy X coordinate +- 2 against asteroid X coordinate +- 4
            if ((enePos.getX() - 5 < asPos.getX() + 4) && (enePos.getX() + 5 > asPos.getX() - 4)) {
                // Check projectile Y coordinate +- 2 against asteroid Y coordinate +- 4
                if ((enePos.getY() - 5 < asPos.getY() + 4) && (enePos.getY() + 5 > asPos.getY() - 4)) {
                    System.out.println("Collision Detected with asteroid and enemy");
                    world.removeEntity(as);
                    LifePart eneLp = ene.getPart(LifePart.class);
                    if (eneLp.isIsHit()) {
                        world.removeEntity(ene);
                        return;
                    }
                    eneLp.setIsHit(true);
                }
            }
        }
        // Asteroid collision check with Projectile
        if (pr != null) {
            // Get Projectile Position Part
            PositionPart prPos = pr.getPart(PositionPart.class);
            // Check projectile X coordinate +- 2 against asteroid X coordinate +- 4
            if ((prPos.getX() - 2 < asPos.getX() + 4) && (prPos.getX() + 2 > asPos.getX() - 4)) {
                // Check projectile Y coordinate +- 2 against asteroid Y coordinate +- 4
                if ((prPos.getY() - 2 < asPos.getY() + 4) && (prPos.getY() + 2 > asPos.getY() - 4)) {
                    System.out.println("Collision Detected with asteroid and projectile");
                    world.removeEntity(pr);

                    // Check if asteroid is small(i.e. it has been hit before), then just destroy the asteroid and return
                    LifePart lp = as.getPart(LifePart.class);
                    world.removeEntity(as);
                    if (lp.isIsHit()) {
                        return;
                    }
                    world.addEntity(createAsteroid(asPos));
                    world.addEntity(createAsteroid(asPos));
                }
            }
        }
    }

    private Entity createAsteroid(PositionPart position) {
        float deacceleration = 0;
        float acceleration = 200;
        float maxSpeed = 200;
        float rotationSpeed = 0;
        float x = position.getX();
        float y = position.getY();
        float radians = position.getRadians() / (float) ((Math.random() * 2) + 1);

        Entity asteroid = new Asteroid();
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        LifePart lp = new LifePart(1, 0);
        lp.setIsHit(true);
        asteroid.add(lp);

        return asteroid;
    }

}
