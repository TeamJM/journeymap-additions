package journeymapadditions.network.packets;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.network.ChunkInfoHandler;
import journeymapadditions.network.data.PacketContext;
import journeymapadditions.network.data.Side;
import journeymapadditions.network.dispatch.ServerNetworkDispatcher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;

import static journeymapadditions.JourneymapAdditions.MOD_ID;

public class ChunkInfoPacket
{
    public static final ResourceLocation CHANNEL = new ResourceLocation(MOD_ID, "chunk_info");
    private ChunkPos chunkPos;
    private Boolean slimeChunk;


    public ChunkInfoPacket()
    {
    }

    public ChunkInfoPacket(Boolean slimeChunk, ChunkPos chunkPos)
    {
        this.slimeChunk = slimeChunk;
        this.chunkPos = chunkPos;
    }

    public static ChunkInfoPacket decode(FriendlyByteBuf buf)
    {
        ChunkInfoPacket packet = new ChunkInfoPacket();
        try
        {
            if (buf.capacity() > 1)
            {
                packet.slimeChunk = buf.readBoolean();
                packet.chunkPos = buf.readChunkPos();
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(String.format("Failed to read message for teleport packet: %s", t));
        }
        return packet;
    }

    public void encode(FriendlyByteBuf buf)
    {
        try
        {
            buf.writeBoolean(this.slimeChunk);
            buf.writeChunkPos(this.chunkPos);
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error("[toBytes]Failed to write message for teleport packet:" + t);
        }
    }

    public ChunkPos getChunkPos()
    {
        return chunkPos;
    }

    public Boolean isSlimeChunk()
    {
        return slimeChunk;
    }

    //
    public static void handle(PacketContext<ChunkInfoPacket> ctx)
    {
        if (Side.SERVER.equals(ctx.side()))
        {
            ServerNetworkDispatcher.sendChunkInfoPacket(ctx.sender(), ctx.message().getChunkPos());
        }
        else
        {
            ChunkInfoHandler.handle(ctx.message());
        }
    }
}
