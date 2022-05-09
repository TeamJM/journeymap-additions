package journeymapadditions.network.dispatch;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.network.packets.ChunkInfoPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

public class ServerNetworkDispatcher
{
    public static void sendChunkInfoPacket(ServerPlayer player, ChunkPos chunkPos)
    {
        LevelChunk chunk = player.level.getChunk(chunkPos.x, chunkPos.z);
        JourneymapAdditions.getInstance().getNetworkHandler().sendToClient(new ChunkInfoPacket(JourneymapAdditions.isSlimeChunk(chunk), chunkPos), player);
    }

}

