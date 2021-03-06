package org.bukkit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

import org.bukkit.BlockType.Default;
import org.junit.Test;

public class BlockTypeTest {
    @Test
    public void getZeroReturnsABlockType() {
        BlockType subject = BlockType.get(0);

        assertThat(subject, isA(BlockType.class));
    }

    @Test(expected=IllegalArgumentException.class)
    public void getMinusOneFails() {
        BlockType.get(-1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getOverTwoHundredAndFiftyFiveFails() {
        BlockType.get(256);
    }

    @Test
    public void registerABlockTypeSucceedsAndReturnsIdenticalObject() {
        BlockType subject = new BlockType(0) {
            @Override public String getInternalName() { return null; }
        };

        BlockType.register(subject);

        assertThat(subject, is(BlockType.get(0)));
    }

    @Test
    public void staticsContainBlockTypes() {
        BlockType subject = BlockType.STONE;

        assertThat(subject, isA(BlockType.class));
    }

    @Test(expected=NullPointerException.class)
    public void beforeRegistrationGetInternalNameThrowsNPE() {
        BlockType.STONE.getInternalName();
    }

    @Test
    public void afterRegisterFinalStaticAreUpdated() {
        BlockType subject = new BlockType(1) {
            @Override
            public String getInternalName() {
                return "stone";
            }
        };

        BlockType.register(subject);

        assertThat(subject, is(BlockType.get(1)));
        assertThat(BlockType.STONE, is(BlockType.get(1)));
        assertThat(BlockType.STONE.getInternalName(), is(subject.getInternalName()));
    }

    @Test
    public void enumContainsCorrectData() {
        BlockType subject = BlockType.get(Default.STONE.getId());

        assertThat(subject, is(BlockType.STONE));
    }

    @Test
    public void getByEnumReturnsCorrectData() {
        BlockType subject = BlockType.get(Default.STONE);

        assertThat(subject, is(BlockType.STONE));
    }

    @Test
    public void eachBlockTypeKnowsItsEnumType() {
        assertThat(BlockType.STONE.getType(), is(BlockType.Default.STONE));
        assertThat(BlockType.GRASS.getType(), is(BlockType.Default.GRASS));
    }

    @Test
    public void customTypesReturnDefaultCustom() {
        Default subject = BlockType.get(255).getType();

        assertThat(subject, is(BlockType.Default.CUSTOM));
    }

    @Test
    public void getByCustomReturnsAir() {
        BlockType subject = BlockType.get(BlockType.Default.CUSTOM);

        assertThat(subject, is(BlockType.get(0)));
    }

    @Test
    public void getByNameForStoneWorks() {
        BlockType subject = BlockType.get("STONE");

        assertThat(subject, is(BlockType.STONE));
    }

    @Test
    public void getByNameForUnknownAliasesWorks() {
        BlockType subject = BlockType.get("FOOBAR");

        assertThat(subject, is(BlockType.get(0)));
    }

    @Test
    public void aliasesShouldBeCaseInsensitive() {
        BlockType subject = BlockType.get("stOnE");

        assertThat(subject, is(BlockType.get("STONE")));
    }

    @Test
    public void addingMultipleAliasesWorks() {
        BlockType.STONE.addAlias("Brick", "Rock", "Hard Stuffs");

        assertThat(BlockType.get("Brick"), is(BlockType.STONE));
        assertThat(BlockType.get("Rock"), is(BlockType.STONE));
        assertThat(BlockType.get("Hard Stuffs"), is(BlockType.STONE));
    }

}
