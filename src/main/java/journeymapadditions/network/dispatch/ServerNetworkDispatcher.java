package journeymapadditions.network.dispatch;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.network.PacketRegistry;
import journeymapadditions.network.packet.ChunkInfoPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

import static net.minecraftforge.fmllegacy.network.NetworkDirection.PLAY_TO_CLIENT;

public class ServerNetworkDispatcher
{
    public static void sendChunkInfoPacket(ServerPlayer player, ChunkPos chunkPos)
    {
        LevelChunk chunk = player.level.getChunk(chunkPos.x, chunkPos.z);
        PacketRegistry.REGISTRY.sendTo(new ChunkInfoPacket(JourneymapAdditions.isSlimeChunk(chunk), chunkPos), player.connection.connection, PLAY_TO_CLIENT);
    }

}

