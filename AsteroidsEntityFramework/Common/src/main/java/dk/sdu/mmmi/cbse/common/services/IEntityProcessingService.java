package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Interface defining Entity Processing methods. Used by entities to ensure they are processed by game cycles.
 * @author chris
 */
public interface IEntityProcessingService {

    /**
     * process is called every game cycle to process Entities
     * Pre-condition: Entities must exist in the game.
     * Post-condition: Entities existing in the game has had their internal logics processed.
     * @param gameData Object storing data such as Delta Time from last game update and game screens x,y size
     * @param world The World object storing data of all entities in the game currently
     */
    void process(GameData gameData, World world);
}
