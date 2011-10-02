package org.bukkit;

public abstract class BlockType {
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
    private static final BlockType[] byId = new BlockType[256];
    static {
        for (int id = 0; id < byId.length; id++) {
            byId[id] = new BlockTypeWrapper(id);
        }
    }

    private final int id;

    public BlockType(int id) {
        this.id = id;
    }

    public static BlockType get(int id) {
        return byId[id];
    }

    public static void register(BlockType blockType) {
        ((BlockTypeWrapper) byId[blockType.getId()]).setType(blockType);
    }

    public static final BlockType STONE = byId[1];

    public final int getId() {
        return id;
    }

    public abstract String getInternalName();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockType)) return false;
        BlockType blockType = (BlockType) obj;

        return blockType.getId() == this.getId();
    }

}
