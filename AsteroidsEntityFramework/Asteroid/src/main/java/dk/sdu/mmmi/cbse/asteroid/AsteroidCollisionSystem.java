/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

/**
 *
 * @author Agger
 */
public class AsteroidCollisionSystem implements IPostEntityProcessingService {

    /**
     * Asteroid should be split up into 2 smaller Asteroids if hit by EnemyProjectile or PlayerProjectile.
     * @param gameData
     * @param world 
     */
    @Override
    public void process(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
