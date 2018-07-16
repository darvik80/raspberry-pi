package xyz.crearts.rover.service;

import xyz.crearts.dto.MotorSide;
import xyz.crearts.dto.MovingDirection;

public interface MotorControllerService {
    void move(MotorSide motor, MovingDirection direction, int speed);
}
