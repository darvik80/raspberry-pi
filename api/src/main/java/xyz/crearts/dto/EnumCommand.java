package xyz.crearts.dto;

public enum EnumCommand {
    CMD_MOVE;

    public static EnumCommand get(byte b) {
        return EnumCommand.values()[b];
    }
}
