package xyz.crearts.dto.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import xyz.crearts.dto.Response;

import java.util.List;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

public class ResponseDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws IndexOutOfBoundsException {
        list.add(Response.builder()
                .code(byteBuf.readShort())
                .message(Utils.readString(byteBuf))
                .build()
        );
    }

}
