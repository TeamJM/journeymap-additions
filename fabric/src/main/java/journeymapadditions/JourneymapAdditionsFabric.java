package journeymapadditions;

import journeymapadditions.event.FabricEventHandler;
import journeymapadditions.network.FabricNetworkHandler;
import journeymapadditions.network.data.Side;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class JourneymapAdditionsFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
            JourneymapAdditions.getInstance().setNetworkHandler(new FabricNetworkHandler(Side.SERVER));
            new FabricEventHandler(Side.SERVER);
        });
        ClientLifecycleEvents.CLIENT_STARTED.register((client) -> {
            JourneymapAdditions.getInstance().setNetworkHandler(new FabricNetworkHandler(Side.CLIENT));
            new FabricEventHandler(Side.CLIENT);
        });

    }
}
