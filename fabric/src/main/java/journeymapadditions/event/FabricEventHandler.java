package journeymapadditions.event;

import journeymap.client.api.event.fabric.FabricEvents;
import journeymap.client.api.event.fabric.FullscreenDisplayEvent;
import journeymap.client.api.event.fabric.PopupMenuEvent;
import journeymapadditions.JourneymapAdditions;
import journeymapadditions.events.EventHandler;
import journeymapadditions.network.data.Side;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

public class FabricEventHandler
{
    private final EventHandler handler;

    public FabricEventHandler(Side side)
    {
        this.handler = new EventHandler();
        if (Side.SERVER.equals(side))
        {
            ServerChunkEvents.CHUNK_LOAD.register(this::onChunkLoadEvent);
            ServerChunkEvents.CHUNK_UNLOAD.register((this::onChunkUnloadEvent));
        }
        else
        {
            FabricEvents.ADDON_BUTTON_DISPLAY_EVENT.register(this::onFullscreenAddonButton);
            FabricEvents.FULLSCREEN_POPUP_MENU_EVENT.register(this::onPopupMenuEvent);
        }
    }

    private void onPopupMenuEvent(PopupMenuEvent.FullscreenPopupMenuEvent event)
    {
        try
        {
            if (!event.isCanceled())
            {
                handler.onPopupEvent(event.getPopupMenu());
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }

    private void onFullscreenAddonButton(FullscreenDisplayEvent.AddonButtonDisplayEvent event)
    {
        handler.onItemDisplayEvent(event.getThemeButtonDisplay());
    }

    private void onChunkUnloadEvent(ServerLevel serverLevel, LevelChunk levelChunk)
    {
        handler.onChunkUnloadEvent(levelChunk.getPos());
    }

    private void onChunkLoadEvent(ServerLevel serverLevel, LevelChunk levelChunk)
    {
        try
        {
            if (serverLevel != null)
            {
                ChunkPos chunkPos = levelChunk.getPos();
                handler.onChunkLoadEvent(chunkPos);
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(t.getMessage(), t);
        }
    }
}
