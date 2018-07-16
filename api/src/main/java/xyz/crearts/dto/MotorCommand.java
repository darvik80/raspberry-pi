package xyz.crearts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MotorCommand implements Command {
    private int speed;
    private MovingDirection direction;
    private MotorSide motor;

    @Override
    public ModuleType module() {
        return ModuleType.MOTOR;
    }
}
