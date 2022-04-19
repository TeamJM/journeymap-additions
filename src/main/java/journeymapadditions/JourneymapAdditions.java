package journeymapadditions;

import journeymapadditions.client.forge.ForgeEvents;
import journeymapadditions.client.integration.ClientProperties;
import journeymapadditions.network.PacketRegistry;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(JourneymapAdditions.MOD_ID)
public class JourneymapAdditions
{
    public static final String MOD_ID = "journeymapadditions";
    private static JourneymapAdditions instance;
    private ClientProperties clientProperties;
    public JourneymapAdditions()
    {
        instance = this;
        // Setup the Client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.register(new ForgeEvents()));

        // First Event
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetupEvent);
    }

    public static JourneymapAdditions getInstance() {
        return instance;
    }

    public ClientProperties getClientProperties()
    {
        return clientProperties;
    }

    public void setClientProperties(ClientProperties clientProperties)
    {
        this.clientProperties = clientProperties;
    }

    /**
     * Common setup event.
     *
     * @param event the event
     */
    public void commonSetupEvent(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            getLogger().info("Initializing Packet Registries");
            PacketRegistry.init();
        });
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
}
