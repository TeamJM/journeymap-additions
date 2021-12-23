package journeymapadditions.network.packet;

import journeymapadditions.JourneymapAdditions;
import journeymapadditions.client.ChunkInfoHandler;
import journeymapadditions.network.dispatch.ServerNetworkDispatcher;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ChunkInfoPacket
{

    private ChunkPos chunkPos;
    private Boolean slimeChunk;

    public ChunkInfoPacket(Boolean slimeChunk, ChunkPos chunkPos)
    {
        this.slimeChunk = slimeChunk;
        this.chunkPos = chunkPos;
    }

    public ChunkInfoPacket(PacketBuffer buf)
    {
        try
        {
            if (buf.capacity() > 1)
            {
                this.slimeChunk = buf.readBoolean();
                this.chunkPos = new ChunkPos(buf.readLong());
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(String.format("Failed to read message for teleport packet: %s", t));
        }
    }

    public void encode(PacketBuffer buf)
    {
        try
        {
            buf.writeBoolean(this.slimeChunk);
            buf.writeLong(chunkPos.toLong());
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

    public static void handle(ChunkInfoPacket packet, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide().isServer())
            {
                ServerNetworkDispatcher.sendChunkInfoPacket(ctx.get().getSender(), packet.chunkPos);
            }
            else
            {
                ChunkInfoHandler.handle(packet);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
