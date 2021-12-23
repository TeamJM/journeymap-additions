package journeymapadditions.client.forge;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.network.dispatch.ClientNetworkDispatcher;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEvents
{
    /**
     * Listen for Forge chunk load, show polygon overlay if it is a slime chunk.
     */
    @SubscribeEvent
    public void onChunkLoadEvent(ChunkEvent.Load event)
    {
        try
        {
            if (event.getWorld() != null)
            {
                ChunkPos chunkPos = event.getChunk().getPos();
                ClientNetworkDispatcher.sendChunkInfoRequest(chunkPos);
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }
}
