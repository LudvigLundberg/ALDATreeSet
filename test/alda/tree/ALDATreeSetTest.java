package alda.tree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ALDATreeSetTest {
    ALDATreeSet<Integer> tree = new ALDATreeSet<>();

    @Before
    public void setUp() throws Exception {
        tree.add(5);
        tree.add(4);
        tree.add(2);
        tree.add(3);
        tree.add(6);
        tree.add(1);
    }

    @Test
    public void testSize(){
        assertEquals(tree.size(), 6);
    }

    @Test
    public void testAddDuplicates() {
        for (int n = 1; n <= 6; n += 2)
            assertFalse(tree.add(n));
    }

    @Test
    public void testToString() {
        assertEquals("[1, 2, 3, 4, 5, 6]", tree.toString());
    }


    @Test
    public void testAddUnique() {
        for (int n = 1; n <= 6; n++) {
            assertTrue(tree.contains(n));
        }
    }

    @Test
    public void testSizeAfterAddingExistingElement(){
        assertFalse(tree.add(5));
        assertEquals(tree.size(), 6);
    }



}