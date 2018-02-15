/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

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
        
    }
    
}
