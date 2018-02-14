/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.projectile;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Agger
 */
public class Projectile extends Entity {
    private int maxDuration = 250;
    private int duration = 0;
    
    public boolean isExpired(){
        return duration == maxDuration;
    }
    
    public void increaseDuration(){
        duration++;
    }
}
