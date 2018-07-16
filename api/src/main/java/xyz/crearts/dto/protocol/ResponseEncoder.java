package xyz.crearts.dto.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xyz.crearts.dto.Response;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

public class ResponseEncoder extends MessageToByteEncoder<Response> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws IndexOutOfBoundsException {
        byteBuf.writeShort(response.getCode());
        byteBuf.writeBytes(Utils.writeString(response.getMessage()));
    }

}
