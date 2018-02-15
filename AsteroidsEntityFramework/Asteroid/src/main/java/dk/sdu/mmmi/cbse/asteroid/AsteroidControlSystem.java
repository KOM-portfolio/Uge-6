/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author chris
 */
public class AsteroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            movingPart.setLeft(false);
            movingPart.setRight(false);
            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            updateShape(asteroid);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        LifePart lp = entity.getPart(LifePart.class);
        if (lp.isIsHit()) {
            shapex[0] = (float) (x + Math.cos(radians) * 2);
            shapey[0] = (float) (y + Math.sin(radians) * 2);

            shapex[1] = (float) (x + Math.cos(radians + 3.1415f * 0.5) * 2);
            shapey[1] = (float) (y + Math.sin(radians + 3.1145f * 0.5) * 2);

            shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 2);
            shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 2);

            shapex[3] = (float) (x + Math.cos(radians + 3.1415f * 1.5) * 2);
            shapey[3] = (float) (y + Math.sin(radians + 3.1415f * 1.5) * 2);
        } else {
            shapex[0] = (float) (x + Math.cos(radians) * 5);
            shapey[0] = (float) (y + Math.sin(radians) * 5);

            shapex[1] = (float) (x + Math.cos(radians + 3.1415f * 0.5) * 5);
            shapey[1] = (float) (y + Math.sin(radians + 3.1145f * 0.5) * 5);

            shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
            shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

            shapex[3] = (float) (x + Math.cos(radians + 3.1415f * 1.5) * 5);
            shapey[3] = (float) (y + Math.sin(radians + 3.1415f * 1.5) * 5);
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
