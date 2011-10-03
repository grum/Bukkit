package org.bukkit;

import java.util.HashMap;
import java.util.Map;

public abstract class BlockType {
    public enum Default {
        STONE(1),
        GRASS(2),
        CUSTOM;

        private int id;

        Default() {}
        Default(int id) {
            this.id = id;
            idToEnum[id] = this;
            addAlias(id, this.toString());
        }

        public int getId() {
            return id;
        }
    }

    // Wrapper object so we can 'change' static finals.
    private static class Wrapper extends BlockType {
        protected Wrapper(int id) {
            super(id);
        }

        private BlockType type;

        private void setType(BlockType type) {
            this.type = type;
        }

        @Override
        public String getInternalName() {
            return type.getInternalName();
        }
    }

    // Container + Init
    private static final int MAX = 256;
    private static final Default[] idToEnum = new Default[MAX];
    private static final Map<String, Integer> byName = new HashMap<String, Integer>();
    private static final BlockType[] byId = new BlockType[MAX];
    static {
        for (int id = 0; id < byId.length; id++) {
            byId[id] = new Wrapper(id);
        }
    }

    // Define constants for ease of use.
    public static final BlockType STONE = byId[Default.STONE.getId()];
    public static final BlockType GRASS = byId[Default.GRASS.getId()];

    public static void register(BlockType type) {
        ((Wrapper) byId[type.getId()]).setType(type);
    }

    private static void addAlias(int typeIndex, String... aliases) {
        for (String alias : aliases) {
            byName.put(alias.toLowerCase(), typeIndex);
        }
    }

    public void addAlias(String... aliases) {
        addAlias(this.getId(), aliases);
    }

    private final int id;

    public BlockType(int id) {
        this.id = id;
    }

    public static BlockType get(int id) {
        if (id < 0 || id >= MAX) {
            throw new IllegalArgumentException();
        }
        return byId[id];
    }

    public static BlockType get(Default type) {
        return get(type.getId());
    }

    public static BlockType get(String name) {
        Integer id = byName.get(name.toLowerCase());
        return get(id == null ? 0 : id);
    }

    // Accessors
    public final int getId() {
        return id;
    }

    public Default getType() {
        Default type = idToEnum[id];

        return type == null ? Default.CUSTOM : type;
    }

    public abstract String getInternalName();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockType)) {
            return false;
        }
        BlockType type = (BlockType) obj;

        return type.getId() == this.getId();
    }

    @Override
    public String toString() {
        return String.format("BlockType.%s, id: %d", this.getType(), this.getId());
    }
}
