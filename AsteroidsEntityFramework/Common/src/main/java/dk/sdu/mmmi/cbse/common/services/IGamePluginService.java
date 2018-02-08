package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Interface used to define game plugins which is allowed to be installed/uninstalled during runtime
 * @author chris
 */
public interface IGamePluginService {
    /**
     * start being called when the game plugin is installed to the game.
     * Pre-condition: The game is running and the World object may not be null.
     * Post-condition: The plugin has been installed into the game without it crashing.
     * @param gameData Object storing data such as Delta Time from last game update and game screens x,y size
     * @param world The World object storing data of all entities in the game currently
     */
    void start(GameData gameData, World world);

    /**
     * stop being called when the game plugin is uninstalled from the game.
     * This is to cleanup anything left by the plugin.
     * Pre-condition: The plugin must be installed in the game
     * Post-condition: The plugin is uninstalled from the game without it crashing.
     * @param gameData  Object storing data such as Delta Time from last game update and game screens x,y size
     * @param world The World object storing data of all entities in the game currently
     */
    void stop(GameData gameData, World world);
}
