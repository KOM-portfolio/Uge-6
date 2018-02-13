/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

/**
 *
 * @author Agger
 */
public class CollisionControlSystem implements IPostEntityProcessingService {

    /**
     * Method here should calculate collision for all entites in the world currently.
     * It should read the data and find any collisions, then create new data and add it to the world.
     * i.e. if an asteroid is hit by a projectile, the asteroid should split in two smaller ones.
     * @param gameData
     * @param world 
     */
    @Override
    public void process(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
