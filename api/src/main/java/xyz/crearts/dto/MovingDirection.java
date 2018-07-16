package xyz.crearts.dto;

/**
 * @author Ivan Kishchenko (email: ivan.kishchenko@lazada.com)
 */

public enum MovingDirection {
    DIR_FORWARD,
    DIR_BACKWARD;
    public static MovingDirection get(byte b) {
        return MovingDirection.values()[b];
    }

}
