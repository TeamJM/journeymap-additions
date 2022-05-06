package journeymapadditions.network.packet;

import journeymapadditions.ChunkInfoHandler;
import journeymapadditions.JourneymapAdditions;
import journeymapadditions.network.dispatch.ServerNetworkDispatcher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.network.NetworkEvent;

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

    public ChunkInfoPacket(FriendlyByteBuf buf)
    {
        try
        {
            if (buf.capacity() > 1)
            {
                this.slimeChunk = buf.readBoolean();
                this.chunkPos = buf.readChunkPos();
            }
        }
        catch (Throwable t)
        {
            JourneymapAdditions.getLogger().error(String.format("Failed to read message for teleport packet: %s", t));
        }
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
