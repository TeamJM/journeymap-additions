package journeymapadditions.network.dispatch;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.network.PacketRegistry;
import journeymapadditions.network.packet.ChunkInfoPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;

public class ServerNetworkDispatcher
{
    public static void sendChunkInfoPacket(ServerPlayerEntity player, ChunkPos chunkPos)
    {
        Chunk chunk = player.level.getChunk(chunkPos.x, chunkPos.z);
        PacketRegistry.REGISTRY.sendTo(new ChunkInfoPacket(JourneymapAdditions.isSlimeChunk(chunk), chunkPos), player.connection.connection, PLAY_TO_CLIENT);
    }

}

