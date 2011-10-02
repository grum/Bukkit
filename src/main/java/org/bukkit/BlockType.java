package org.bukkit;

public abstract class BlockType {

    private static BlockType[] byId = new BlockType[256];
    static {
        for (int id = 0; id < byId.length; id++) {
            byId[id] = new BlockType(id) {};
        }
    }

    private final int id;

    public BlockType(int id) {
        this.id = id;
    }

    public static BlockType get(int id) {
        return byId[id];
    }

    public static void register(int id, BlockType blockType) {
        byId[id] = blockType;
    }

    public static final BlockType STONE = byId[1];

    public final int getId() {
        return id;
    }
}
