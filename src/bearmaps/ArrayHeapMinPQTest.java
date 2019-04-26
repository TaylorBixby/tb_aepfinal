package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest<T> {

//    1
    @Test
    public void removeMethodsWorkForSimpleHeap() {
        ArrayHeapMinPQ sand = new ArrayHeapMinPQ();
        sand.add(1, 0.5);
        sand.add(2, 1.1);
        sand.add(3, 2.0);
        assertEquals(1, sand.removeSmallest());
        assertEquals(2, sand.removeSmallest());
        assertEquals(3, sand.removeSmallest());
    }

//  7
    @Test
    public void testBothMethodsOfGet() {
        ArrayHeapMinPQ sand = new ArrayHeapMinPQ();
        sand.add(1, 0.5);
        sand.add(2, 1.1);
        sand.add(3, 2.0);
        assertEquals(1, sand.getSmallestNode().name);
        assertEquals(1, sand.getSmallest());
    }

//  9
    @Test(expected = java.util.NoSuchElementException.class)
    public void removeFromEmtpyArrayThrowsCorrectError () {
        ArrayHeapMinPQ emptyarray = new ArrayHeapMinPQ();
        emptyarray.removeSmallest();
    }

    //  6
    @Test
    public void printHeap() {
        ArrayHeapMinPQ sand = new ArrayHeapMinPQ();
        sand.add(1, 0.5);
        sand.add(2, 1.1);
        sand.add(3, 2.0);
        sand.printHeap();
    }

    private ArrayHeapMinPQ createOneArray() {
        ArrayHeapMinPQ one = new ArrayHeapMinPQ();
        one.add("_.5", 0.5);
        one.add("_1.1", 1.1);
        one.add("_2.0", 2.0);
        one.add("_2.5", 2.5);
        one.add("_2.3", 2.3);
        one.add("_1.6", 1.6);
        one.add("_3.4", 3.4);
        one.add("_5_or_1.5", 5);

        return one;
    }

//  8
    @Test
    public void addingSameHeapItemNameWithDifferentValueIsValid() {
        ArrayHeapMinPQ one = createOneArray();
        one.add("second.5", .5);
        T a = (T) one.removeSmallest();
//        System.out.println(a);
        assertEquals("_.5", a);
        T b = (T) one.removeSmallest();
//        System.out.println(b);
        assertEquals("second.5", b);
    }

//  2
    @Test
    public void getSmallestReturnsSmallestChecksInitialInsertionWorks() {
        ArrayHeapMinPQ one = createOneArray();
        ArrayHeapMinPQ.HeapItem a = one.getSmallestNode();
//        System.out.println(a.showName() + " " + a.showpri());
        assertEquals(a.showpri(), 0.5, .001);
        assertEquals(a.showName(), "_.5");
        //size = 8
//        System.out.println(one.size());
    }

//  3
    @Test
    public void swinAndSinkPrivetMethodsTestedThroughRemovingSmallest() {
        ArrayHeapMinPQ one = createOneArray();
        one.removeSmallest();
        //test for correct change
        ArrayHeapMinPQ.HeapItem b = one.getSmallestNode();
//            System.out.println(b.showName() + " " + b.showpri());
        assertEquals(1.1, b.showpri(), .001);
        assertEquals("_1.1", b.showName());
        //size #2 = 7
//            System.out.println(one.size());
    }

//  4
    @Test
    public void changePrioityTest() {
        ArrayHeapMinPQ one = createOneArray();

        one.changePriority("_5_or_1.5", 1.5);
        one.removeSmallest();
        one.removeSmallest();
        //test for correct change
        ArrayHeapMinPQ.HeapItem c = one.getSmallestNode();
//      System.out.println(c.showName() + " " + c.showpri());
        assertEquals(1.5, c.showpri(), .001);
        assertEquals("_5_or_1.5", c.showName());
        assertEquals(6, one.size());
    }

//  5
    @Test
    public void containsTest() {
        ArrayHeapMinPQ one = createOneArray();
        assertTrue(one.contains("_2.5"));
        one.removeSmallest();
        assertFalse(one.contains("_.5"));
//        one.printHeap();
    }

//    longer for loop tests with seed setpoints
//    long a = setSeed(long s);
//    Sets the seed of the pseudo-random number generator.

//  10
    @Test
    public void speedTestContainsRuntimeEfficent() {
    //contains works
    //to check: change priority
        //create my heap
        Stopwatch addmine = new Stopwatch();
        ArrayHeapMinPQ mine = new ArrayHeapMinPQ();
        for (int i = 0; i < 20000; i += 1) {
            mine.add(i, StdRandom.uniform(0.0,10000000.0));
        }
        System.out.println("Total time elapsed for add-mine: " + addmine.elapsedTime() +  " seconds.");

        //create neive heap
        Stopwatch addnieve = new Stopwatch();
        NaiveMinPQ nieve = new NaiveMinPQ();
        for (int i = 0; i < 20000; i += 1) {
            nieve.add(i, StdRandom.uniform(0.0,10000000.0));
        }
        System.out.println("Total time elapsed for add-naive: " + addnieve.elapsedTime() +  " seconds.");

        //creating new priorities
        ArrayList newpriotiy = new ArrayList(11000);
        for (int i = 0; i < 10000; i += 1) {
            double x =  StdRandom.uniform(0.0,10000000.0);
            newpriotiy.add(i, x);
        }

        Stopwatch containsmine = new Stopwatch();
        for (int i = 0; i < 10000; i += 1) {
            int x =  StdRandom.uniform(0,19000);
            double newpior = (double) newpriotiy.get(i);
            mine.changePriority(x, newpior);
        }
        System.out.println("Total time elapsed for contains-mine: " + containsmine.elapsedTime() +  " seconds.");

        Stopwatch cointainsnieve = new Stopwatch();
        for (int i = 0; i < 10000; i += 1) {
            int x =  StdRandom.uniform(0,19000);
            double newpior = (double) newpriotiy.get(i);
            nieve.changePriority(x, newpior);
        }
        System.out.println("Total time elapsed for contains-naive: " + cointainsnieve.elapsedTime() +  " seconds.");
    }

}