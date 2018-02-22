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
import dk.sdu.mmmi.cbse.enemy.Enemy;
import dk.sdu.mmmi.cbse.projectile.PlayerProjectile;
import dk.sdu.mmmi.cbse.projectile.Projectile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Agger
 */
public class EnemyCollisionSystem implements IPostEntityProcessingService {

    /**
     * Enemy ship should be destroyed if hit by an Asteroid or by a PlayerProjectile twice
     * @param gameData
     * @param world 
     */
    @Override
    public void process(GameData gameData, World world) {
        List<Entity> enemies = new ArrayList<>(world.getEntities(Enemy.class));
        List<Entity> foreignObjects = new ArrayList<>();
        foreignObjects.addAll(world.getEntities(PlayerProjectile.class));

        for (Entity e : enemies) {
            Enemy ene = (Enemy) e;
            // Check collision for each foreign Object we want something to happen with on collision
            for (Entity obj : foreignObjects) {
                this.checkCollision(world, ene, obj);
            }
        }
    }
    
    private void checkCollision(World world, Entity enemy, Entity foreign) {
        Enemy ene = (Enemy) enemy;
        Projectile pr = null;
        if ((foreign instanceof PlayerProjectile)) {
            pr = (Projectile) foreign;
        }

        // Get Enemy Position part
        PositionPart enePos = ene.getPart(PositionPart.class);
        
        // Enemy collision check with PlayerProjectile
        if (pr != null) {
            // Get Projectile Position Part
            PositionPart prPos = pr.getPart(PositionPart.class);
            // Check PlayerProjectile X coordinate +- 2 against Enemy X coordinate +- 4
            if ((prPos.getX() - 2 < enePos.getX() + 4) && (prPos.getX() + 2 > enePos.getX() - 4)) {
                // Check PlayerProjectile Y coordinate +- 2 against Enemy Y coordinate +- 4
                if ((prPos.getY() - 2 < enePos.getY() + 4) && (prPos.getY() + 2 > enePos.getY() - 4)) {
                    System.out.println("Collision Detected with Enemy and PlayerProjectile");
                    world.removeEntity(pr);

                    // Check if Enemy is already hit, then just destroy the Enemy and return
                    LifePart lp = ene.getPart(LifePart.class);
                    if (lp.isIsHit()) {
                        world.removeEntity(ene);
                        return;
                    }
                    lp.setIsHit(true);
                }
            }
        }
    }
    
}
