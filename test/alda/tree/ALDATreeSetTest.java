package alda.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        assertEquals(6, tree.size());
    }

    @Test
    public void testAddDuplicates() {
        for (int n = 1; n <= 6; n += 2)
            assertFalse(tree.add(n));
    }

    @Test
    public void testHeight(){
        assertEquals(2, tree.height());
    }

    @Test
    public void testHeightAddingNumbers(){
        assertEquals(2, tree.height());
        tree.add(7);
        tree.add(8);
        tree.add(9);
        tree.add(10);
        assertEquals(10, tree.size());
        assertEquals(3, tree.height());
        tree.add(-1);
        tree.add(11);
        tree.add(12);
        assertEquals(13, tree.size());
        assertEquals(4, tree.height());


    }

    @Test
    public void testIteration(){
        int[] testArray = {1,2,3,4,5,6};
        int current = 0;
        for(int e : tree){
            assertEquals(testArray[current++],e);
        }
    }

    @Test
    public void testIterationReverseOrder(){
        tree = new ALDATreeSet<Integer>();
        for (int i = 6; i > 0; i--){
            tree.add(i);
        }
        int[] testArray = {1,2,3,4,5,6};
        int current = 0;
        for(int e : tree){
            assertEquals(testArray[current++],e);
        }
    }


    @Test
    public void testToString() {
        assertEquals("[1, 2, 3, 4, 5, 6]", tree.toString());
    }


    @Test
    public void testDelete(){
        assertTrue(tree.remove(1));
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
        assertEquals(6, tree.size());
    }


    @Test
    public void testEmptyTree() {
        tree.clear();
        assertEquals(0, tree.size());
        assertEquals(-1, tree.height());
        assertEquals("[]", tree.toString());
    }


    @Test
    public void testRemoveExistingLeaf() {
        assertTrue(tree.remove(1));
        assertEquals(5, tree.size());
        assertEquals("[2, 3, 4, 5, 6]", tree.toString());
    }

    @Test
    public void testRemoveExistingMiddleItemWithEmptyRightChild() {
        assertTrue(tree.remove(4));
        assertEquals(5, tree.size());
        assertEquals("[1, 2, 3, 5, 6]", tree.toString());
    }

    @Test
    public void testRemoveExistingMiddleItemWithEmptyLeftChild() {
        tree.add(7);
        assertTrue(tree.remove(6));
        assertEquals(6, tree.size());
        assertEquals("[1, 2, 3, 4, 5, 7]", tree.toString());
    }

    @Test
    public void testRemoveExistingMiddleItemWithTwoChildren() {
        assertTrue(tree.remove(2));
        assertEquals(5, tree.size());
        assertEquals("[1, 3, 4, 5, 6]", tree.toString());
    }

    @Test
    public void testRemoveRoot() {
        assertTrue(tree.remove(5));
        assertEquals(5, tree.size());
        assertEquals("[1, 2, 3, 4, 6]", tree.toString());
    }

    @Test
    public void multipleTestMix(){
        int i = 0;
        while (i < 1000){
            tree.clear();
            try {
                setUp();
            }catch (Exception e){
                throw new AssertionError();
            }
            testRandomAddAndRemove();
            i++;
        }
    }

    @Test
    public void testRandomAddAndRemove() {
        Random rnd = new Random();

        SortedSet<Integer> oracle = new TreeSet<Integer>();
        for (int n = 1; n <= 6; n++)
            oracle.add(n);

        for (int n = 0; n < 1000; n++) {
            int toAdd = rnd.nextInt(10);
            assertEquals(oracle.add(toAdd), tree.add(toAdd));
            int toRemove = rnd.nextInt(10);
            assertEquals(oracle.remove(toRemove), tree.remove(toRemove));
            int checkExists = rnd.nextInt(10);
            assertEquals(oracle.contains(checkExists), tree
                    .contains(checkExists));
            assertEquals(oracle.size(), tree.size());
            assertEquals(oracle.toString(), tree.toString());
            Queue<Integer> queue = new LinkedList<Integer>();

            for (Integer integer : tree) {
                queue.add(integer);
            }
            int counter = 0;
            for (Integer integer : oracle) {
                assertEquals(integer, queue.poll());
            }

        }
    }

    @Test
    public void testOtherType(){
        ALDATreeSet<String> stringTree = new ALDATreeSet<>();
        stringTree.add("D");
        stringTree.add("A");
        assertEquals("[A, D]", stringTree.toString());
        stringTree.add("C");
        assertEquals("[A, C, D]", stringTree.toString());
        stringTree.add("A");
        assertEquals("[A, C, D]", stringTree.toString());
        stringTree.add("B");
        assertEquals(4, stringTree.size());
        assertEquals("[A, B, C, D]", stringTree.toString());
        assertTrue(stringTree.contains("C"));
        stringTree.remove("C");
        assertFalse(stringTree.contains("C"));
    }




}