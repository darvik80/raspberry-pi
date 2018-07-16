package xyz.crearts.dto.protocol;

import io.netty.buffer.Unpooled;
import org.junit.Test;
import xyz.crearts.dto.MotorCommand;
import xyz.crearts.dto.MotorSide;
import xyz.crearts.dto.MovingDirection;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

public class MotorCommandDecoderTest {

    @Test
    public void testDecode() {
        byte[] command = new byte[] {
            0, 1, 0, 0, 0, 127
        };

        List<Object> objects = new ArrayList<>();
        MotorCommandDecoder decoder = new MotorCommandDecoder();
        decoder.decode(null, Unpooled.wrappedBuffer(command), objects);

        MotorCommand cmd = (MotorCommand) objects.get(0);

        assertEquals(MotorSide.MOTOR_LEFT, cmd.getMotor());
        assertEquals(MovingDirection.DIR_BACKWARD, cmd.getDirection());
        assertEquals(127, cmd.getSpeed());
    }
}
