/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.projectile;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

/**
 *
 * @author Agger
 */
public class ProjectilePlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        // Not sure if anything needs to initialize here?
        System.out.println("Projectile plugin initialized.");
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity e : world.getEntities(Projectile.class)){
            world.removeEntity(e);
        }
    }
}
