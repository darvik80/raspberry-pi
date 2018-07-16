package xyz.crearts.dto;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

public enum  MotorSide {
    MOTOR_LEFT,
    MOTOR_RIGHT;

    public static MotorSide get(byte b) {
        return MotorSide.values()[b];
    }
}
