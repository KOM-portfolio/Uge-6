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
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author chris
 */
public class AsteroidPlugin implements IGamePluginService {

    private List<Entity> asteroids = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        for (int i = 0; i < (int) (Math.random() * 20) + 10; i++) {
            Entity asteroid = createAsteroid(gameData);
            asteroids.add(asteroid);
            world.addEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        float deacceleration = 0;
        float acceleration = 200;
        float maxSpeed = 200;
        float rotationSpeed = 0;
        float x = gameData.getDisplayWidth() / (int)((Math.random() * 10) + 1);
        float y = gameData.getDisplayHeight() / (int)((Math.random() * 10) + 1);
        float radians = 3.1415f / (float)((Math.random() * 2) + 1);
        float radius = 2;

        Entity asteroid = new Asteroid();
        asteroid.setRadius(radius);
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(1,0));

        return asteroid;

    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity asteroid : asteroids){
            world.removeEntity(asteroid);
        }
    }

}
