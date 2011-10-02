package org.bukkit;

public abstract class BlockType {
    public enum Default {
        STONE(1),
        GRASS(2);

        private int id;

        Default(int id) {
            this.id = id;
            idToEnum[id] = this;
        }

        public int getId() {
            return id;
        }
    }

    // Wrapper object so we can 'change' static finals.
    private static class BlockTypeWrapper extends BlockType {
        protected BlockTypeWrapper(int id) {
            super(id);
        }

        private BlockType blockType;

        private void setType(BlockType blockType) {
            this.blockType = blockType;
        }

        @Override
        public String getInternalName() {
            return blockType.getInternalName();
        }
    }

    // Container + Init
    private static final Default[] idToEnum = new Default[256];
    private static final BlockType[] byId = new BlockType[256];
    static {
        for (int id = 0; id < byId.length; id++) {
            byId[id] = new BlockTypeWrapper(id);
        }
    }

    // Define constants for ease of use.
    public static final BlockType STONE = byId[Default.STONE.getId()];
    public static final BlockType GRASS = byId[Default.GRASS.getId()];

    public static void register(BlockType blockType) {
        ((BlockTypeWrapper) byId[blockType.getId()]).setType(blockType);
    }

    private final int id;

    public BlockType(int id) {
        this.id = id;
    }

    public static BlockType get(int id) {
        return byId[id];
    }

    public static BlockType get(Default type) {
        return get(type.getId());
    }

    // Accessors
    public final int getId() {
        return id;
    }

    public Default getType() {
        return idToEnum[id];
    }

    public abstract String getInternalName();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockType)) return false;
        BlockType blockType = (BlockType) obj;

        return blockType.getId() == this.getId();
    }
}
