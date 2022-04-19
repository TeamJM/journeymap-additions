package journeymapadditions.client.forge;

import journeymap.client.api.display.IThemeButton;
import journeymap.client.api.event.forge.FullscreenDisplayEvent;
import journeymap.client.api.event.forge.PopupMenuEvent;
import journeymapadditions.JourneymapAdditions;
import journeymapadditions.client.integration.ClientProperties;
import journeymapadditions.client.integration.SlimeChunkOverlayHandler;
import journeymapadditions.network.dispatch.ClientNetworkDispatcher;
import net.minecraft.network.chat.TranslatableComponent;
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

    @SubscribeEvent
    public void onPopupEvent(PopupMenuEvent.FullscreenPopupMenuEvent event)
    {
        try
        {
            if (!event.isCanceled())
            {
                event.getPopupMenu().createSubItemList("Slime Chunks")
                        .addMenuItem("Slime IT", b -> SlimeChunkOverlayHandler.getInstance().disable());
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }

    @SubscribeEvent
    public void onItemDisplayEvent(FullscreenDisplayEvent.AddonButtonDisplayEvent event)
    {
        ClientProperties properties = JourneymapAdditions.getInstance().getClientProperties();
        try
        {

            IThemeButton button = event.getThemeButtonDisplay()
                    .addThemeToggleButton(new TranslatableComponent("jm.additions.slime.button.on").getString(),
                            new TranslatableComponent("jm.additions.slime.button.off").getString(),
                            "slime",
                            properties.slimeChunksEnabled.get(),
                            b -> {
                                b.toggle();
                                properties.slimeChunksEnabled.set(b.getToggled());
                            });

        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }

    /**
     * Listen for Forge chunk unload, remove polygon overlay if it is a slime chunk.
     */
    @SubscribeEvent
    public void onChunkUnloadEvent(ChunkEvent.Unload event)
    {
        SlimeChunkOverlayHandler.getInstance().remove(event.getChunk().getPos());
    }
}
