package org.bukkit;

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
    public void minusOneFails() {
        BlockType.get(-1);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void overTwoHundredAndFiftyFiveFails() {
        BlockType.get(256);
    }
}
