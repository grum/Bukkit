package org.bukkit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BlockTypeTest {
    @Test
    public void getZeroReturnsABlockType() {
        BlockType subject = BlockType.get(0);

        assertThat(subject, isA(BlockType.class));
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void getMinusOneFails() {
        BlockType.get(-1);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void getOverTwoHundredAndFiftyFiveFails() {
        BlockType.get(256);
    }

    @Test
    public void registerABlockTypeSucceedsAndReturnsIdenticalObject() {
        BlockType subject = new BlockType() {};
        BlockType.register( 0, subject );

        assertThat(subject, is(BlockType.get(0)));
    }

    @Test
    public void staticsContainBlockTypes() {
        BlockType subject = BlockType.STONE;
        assertThat(subject, isA(BlockType.class));
    }
}
