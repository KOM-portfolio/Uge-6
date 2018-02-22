/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.projectile.EnemyProjectile;
import dk.sdu.mmmi.cbse.projectile.Projectile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Agger
 */
public class PlayerCollisionSystem implements IPostEntityProcessingService {

    /**
     * Player ship should be destroyed if hit by an Asteroid or EnemyProjectile twice
     * @param gameData
     * @param world 
     */
    @Override
    public void process(GameData gameData, World world) {
        List<Entity> players = new ArrayList<>(world.getEntities(Player.class));
        List<Entity> foreignObjects = new ArrayList<>();
        foreignObjects.addAll(world.getEntities(EnemyProjectile.class));

        for (Entity e : players) {
            Player pla = (Player) e;
            // Check collision for each foreign Object we want something to happen with on collision
            for (Entity obj : foreignObjects) {
                this.checkCollision(world, pla, obj);
            }
        }
    }
    
    private void checkCollision(World world, Entity player, Entity foreign) {
        Player pla = (Player) player;
        Projectile pr = null;
        if ((foreign instanceof EnemyProjectile)) {
            pr = (Projectile) foreign;
        }

        // Get Player Position part
        PositionPart plaPos = pla.getPart(PositionPart.class);
        
        // Player collision check with EnemyProjectile
        if (pr != null) {
            // Get EnemyProjectile Position Part
            PositionPart prPos = pr.getPart(PositionPart.class);
            // Use the distance formula to determine the distance between the player and the asteroid
            // There is a few errors with this way of handling collision, the result from the pythagoras can be 0, which means there must be a faulty collision detected somewhere.
            // We can work around this by casting to (int), which seems to produce a more reliable collision calculation
            double pythagoras = Math.pow((int) prPos.getX() - (int) plaPos.getX(), 2) + Math.pow((int) prPos.getY() - (int) plaPos.getY(), 2);
            // Check for faulty collisions
            if ((int) pythagoras == 0 || ((int) plaPos.getX() == (int) prPos.getY() && (int) plaPos.getY() == (int) prPos.getY())) {
                System.out.println("Faulty collision detected.");
                return;
            }
            // We then make use of the distance formula
            if (Math.sqrt((int) pythagoras) < pla.getRadius() + pr.getRadius()) {
                System.out.println("Collision Detected with Player and EnemyProjectile");
                world.removeEntity(pr);
                LifePart plaLp = pla.getPart(LifePart.class);
                if (plaLp.isIsHit()) {
                    world.removeEntity(pla);
                    return;
                }
                plaLp.setIsHit(true);
            }
        }
    }
    
}
