package journeymapadditions;

import journeymapadditions.events.ForgeEvents;
import journeymapadditions.network.ForgeNetworkHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(JourneymapAdditions.MOD_ID)
public class JourneymapAdditionsForge
{
    public JourneymapAdditionsForge()
    {
        // Setup the Client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.register(new ForgeEvents()));

        // First Event
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetupEvent);
    }

    /**
     * Common setup event.
     *
     * @param event the event
     */
    public void commonSetupEvent(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            JourneymapAdditions.getLogger().info("Initializing Packet Registries");
            new JourneymapAdditions(new ForgeNetworkHandler());
        });
    }

}
