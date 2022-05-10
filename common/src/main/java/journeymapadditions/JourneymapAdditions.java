package journeymapadditions;

import journeymapadditions.integration.ClientProperties;
import journeymapadditions.network.data.NetworkHandler;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JourneymapAdditions
{
    public static final String MOD_ID = "journeymapadditions";
    private NetworkHandler networkHandler;
    private ClientProperties clientProperties;
    private static JourneymapAdditions instance;

    public static JourneymapAdditions getInstance()
    {
        if (instance == null)
        {
            instance = new JourneymapAdditions();
        }
        return instance;
    }

    public NetworkHandler getNetworkHandler()
    {
        return networkHandler;
    }

    public void setNetworkHandler(NetworkHandler networkHandler)
    {
        this.networkHandler = networkHandler;
    }

    public ClientProperties getClientProperties()
    {
        return clientProperties;
    }

    /**
     * Get the common logger.
     *
     * @return the logger
     */
    public static Logger getLogger()
    {
        return LogManager.getLogger(MOD_ID);
    }

    /**
     * Magic formula for slime chunk discovery.
     *
     * @param chunk the chunk
     * @return true if it's a slime chunk
     */
    public static boolean isSlimeChunk(LevelChunk chunk)
    {
        if (!chunk.getLevel().isClientSide())
        {
            return WorldgenRandom.seedSlimeChunk(chunk.getPos().x, chunk.getPos().z, chunk.getLevel().getServer().getWorldData().worldGenSettings().seed(), 987234911L).nextInt(10) == 0;
        }
        return false;
    }

    public void setClientProperties(ClientProperties clientProperties)
    {
        this.clientProperties = clientProperties;
    }
}
