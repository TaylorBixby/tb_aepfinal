package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void addAndRemoveMethodsWorkForSimpleHeap() {
        ArrayHeapMinPQ sand = new ArrayHeapMinPQ();
        sand.add(1, 0.5);
        sand.add(2, 1.1);
        sand.add(3, 2.0);
        assertEquals(1,sand.removeSmallest());
        assertEquals(2,sand.removeSmallest());
        assertEquals(3,sand.removeSmallest());
    }

//        @Test
//    public void basicTests() {
//        ArrayHeapMinPQ one = new ArrayHeapMinPQ();
//        one.add("_.5", 0.5);
//        one.add("_1.1", 1.1);
//        one.add("_2.0", 2.0);
//        one.add("_2.5", 2.5);
//        one.add("_2.3", 2.3);
//        one.add("_1.6", 1.6);
//        one.add("_3.4", 3.4);
//        one.add("_5_or_1.5", 5);
//        // getSmallest
//        ArrayHeapMinPQ.HeapItem a = one.getSmallestNode();
////        System.out.println(a.showName() + " " + a.showpri());
//        assertEquals(a.showpri(),  0.5,.001);
//        assertEquals(a.showName(),  "_.5");
//        //size = 8
////        System.out.println(one.size());
//        //remove smallest
//        one.removeSmallest();
//            //test for correct change
//            ArrayHeapMinPQ.HeapItem b = one.getSmallestNode();
////            System.out.println(b.showName() + " " + b.showpri());
//            assertEquals(1.1, b.showpri(),.001);
//            assertEquals("_1.1", b.showName());
//            //size #2 = 7
////            System.out.println(one.size());
//        //changeprioity
//        one.changePriority("_5_or_1.5", 1.5);
//        one.removeSmallest();
//            //test for correct change
//            ArrayHeapMinPQ.HeapItem c = one.getSmallestNode();
////            System.out.println(c.showName() + " " + c.showpri());
//            assertEquals(1.5, c.showpri(),.001);
//            assertEquals("_5_or_1.5", c.showName());
//            //size #3 = 6
////            System.out.println(one.size());
//        //contains
//        assertTrue(one.contains("_2.5"));
//        assertFalse(one.contains("_.5"));
////        System.out.println(" ______________________________");
////        one.printHeap();
//    }
//    longer for loop tests with seed setpoints
//    long a = setSeed(long s);
//    Sets the seed of the pseudo-random number generator.

//    @Test
//    public void speedTest() {
//    //contains works
//    //to check: change priority
//        //create my heap
//        Stopwatch addmine = new Stopwatch();
//        ArrayHeapMinPQ mine = new ArrayHeapMinPQ();
//        for (int i = 0; i < 20000; i += 1) {
//            mine.add(i, StdRandom.uniform(0.0,10000000.0));
//        }
//        System.out.println("Total time elapsed for add-mine: " + addmine.elapsedTime() +  " seconds.");
//
//        //create neive heap
//        Stopwatch addnieve = new Stopwatch();
//        NaiveMinPQ nieve = new NaiveMinPQ();
//        for (int i = 0; i < 20000; i += 1) {
//            nieve.add(i, StdRandom.uniform(0.0,10000000.0));
//        }
//        System.out.println("Total time elapsed for add-naive: " + addnieve.elapsedTime() +  " seconds.");
//
//        //creating new priorities
//        ArrayList newpriotiy = new ArrayList(11000);
//        for (int i = 0; i < 10000; i += 1) {
//            double x =  StdRandom.uniform(0.0,10000000.0);
//            newpriotiy.add(i, x);
//        }
//
//        Stopwatch containsmine = new Stopwatch();
//        for (int i = 0; i < 10000; i += 1) {
//            int x =  StdRandom.uniform(0,19000);
//            double newpior = (double) newpriotiy.get(i);
//            mine.changePriority(x, newpior);
//        }
//        System.out.println("Total time elapsed for contains-mine: " + containsmine.elapsedTime() +  " seconds.");
//
//        Stopwatch cointainsnieve = new Stopwatch();
//        for (int i = 0; i < 10000; i += 1) {
//            int x =  StdRandom.uniform(0,19000);
//            double newpior = (double) newpriotiy.get(i);
//            nieve.changePriority(x, newpior);
//        }
//        System.out.println("Total time elapsed for contains-naive: " + cointainsnieve.elapsedTime() +  " seconds.");
//    }

}
