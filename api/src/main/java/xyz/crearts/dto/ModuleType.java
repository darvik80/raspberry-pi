package xyz.crearts.dto;

public enum ModuleType {
    MOTOR,
    SENSOR;

    public static ModuleType get(byte b) {
        return ModuleType.values()[b];
    }
}
