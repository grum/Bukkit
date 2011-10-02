package org.bukkit;

public abstract class BlockType {

    private static BlockType[] byId = new BlockType[256];
    static {
        for (int id = 0; id < byId.length; id++) {
            byId[id] = new BlockType() {};
        }
    }

    public static BlockType get(int id) {
        return byId[id];
    }
}
