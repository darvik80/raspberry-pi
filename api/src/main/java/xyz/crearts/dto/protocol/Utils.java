package xyz.crearts.dto.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

public class Utils {

    public static String readString(ByteBuf byteBuf) throws IndexOutOfBoundsException {
        int length = byteBuf.readShort();
        if (length > 0) {
            ByteBuf data = byteBuf.readBytes(length);
            return new String(data.array());
        }

        return null;
    }

    public static ByteBuf writeString(String str) throws IndexOutOfBoundsException {
        int length = 0;
        if (str != null) {
            length = str.length();
        }
        ByteBuf data = Unpooled.buffer();
        data.writeShort(length);
        if (length > 0)  {
            data.writeBytes(str.getBytes());
        }

        return data;
    }
}
