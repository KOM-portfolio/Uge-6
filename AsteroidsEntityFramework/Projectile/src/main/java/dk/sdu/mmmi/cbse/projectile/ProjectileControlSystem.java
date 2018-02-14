/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.projectile;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.ArrayList;

/**
 *
 * @author Agger
 */
public class ProjectileControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        ArrayList<Entity> expiredProjectiles = new ArrayList<>();
        ArrayList<Entity> projectilesInWorld = new ArrayList<>();
        projectilesInWorld.addAll(world.getEntities(EnemyProjectile.class));
        projectilesInWorld.addAll(world.getEntities(PlayerProjectile.class));
        
        for (Entity projectile : projectilesInWorld) {
            PositionPart positionPart = projectile.getPart(PositionPart.class);
            MovingPart movingPart = projectile.getPart(MovingPart.class);

            movingPart.setLeft(false);
            movingPart.setRight(false);
            movingPart.setUp(true);

            movingPart.process(gameData, projectile);
            positionPart.process(gameData, projectile);

            updateShape(projectile);
            if (checkExpiration(projectile)) {
                expiredProjectiles.add(projectile);
            }
        }
        for (Entity e : expiredProjectiles) {
            world.removeEntity(e);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 3);
        shapey[0] = (float) (y + Math.sin(radians) * 3);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 3);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 3);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 2);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 2);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 3);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 3);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    private boolean checkExpiration(Entity entity) {
        if (entity instanceof Projectile) {
            Projectile e = (Projectile) entity;
            if(e.isExpired()){
                return true;
            } else {
                e.increaseDuration();
            }
        }
        return false;
    }
}
