package bearmaps;

import java.util.*;
//import java.junit.
import edu.princeton.cs.algs4.Heap;
import edu.princeton.cs.algs4.Stopwatch;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<HeapItem> minHeap;
    private HashMap<T,Integer> contns;
    private int size;

    public ArrayHeapMinPQ() {
        minHeap = new ArrayList<>();
        HeapItem sental = new HeapItem(null, 0.0); //seems like this will cause problems
        minHeap.add(sental);
        contns = new HashMap<>();
        size = 0;
    }

    @Override
    public void add(T item, double priority) {
        if (minHeap.contains(item)) {
            throw new IllegalArgumentException();
        } else {
            HeapItem newadd = new HeapItem(item, priority);
            size++;
            minHeap.add(size(), newadd); // need to check to make sure it doesnt already exist
            contns.put(newadd.name, size());
            swim(size());
        }
    }

    void swim(int childposition) {
        if (childposition != 1) {
            int parentpos = childposition / 2;
            HeapItem childnode = minHeap.get(childposition);
            HeapItem parentnode = minHeap.get(parentpos);
            if (parentnode.pri > childnode.pri) {
                minHeap.set(parentpos, childnode);
                minHeap.set(childposition, parentnode);

                contns.put(parentnode.name, childposition);

                swim(parentpos);
            } else {
                contns.put(childnode.name, childposition);
            }
        }
    }

    int childDecider(int parentposition) {
        if ((parentposition * 2) <= size()) {
            if ((parentposition * 2 + 1) <= size()) {
                int childpositionleft = parentposition * 2;
                int childpositionright = parentposition * 2 + 1;
                HeapItem childnodeleft = minHeap.get(childpositionleft);
                HeapItem childnoderight = minHeap.get(childpositionright);
                if (childnodeleft.pri <= childnoderight.pri) {
                    return childpositionleft;
                }
                return childpositionright;
            } else {
                return parentposition * 2;
            }
        } else {
            return -1;
        }
    }

    void sink(int parentposition) {
        int childposition = childDecider(parentposition);
        HeapItem parentnode = minHeap.get(parentposition);
        if (childposition != -1) {
            HeapItem childnode = minHeap.get(childposition);
            if (childnode.pri < parentnode.pri) {

                minHeap.set(childposition, parentnode);
                minHeap.set(parentposition, childnode);

                contns.put(childnode.name, parentposition);

                sink(childposition);
            } else {
                contns.put(parentnode.name, parentposition);
            }
        } else {
            contns.remove(parentnode.name);
            contns.put(parentnode.name, parentposition);
        }
    }

    @Override
    public boolean contains(T item) {
        return contns.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        HeapItem x =  minHeap.get(1);
        return  x.name;

    }

    public HeapItem getSmallestNode() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        HeapItem x =  minHeap.get(1);
        return  x;

    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        } else {
            HeapItem smallest = minHeap.get(1);
            HeapItem lastitem = minHeap.get(size());
            contns.remove(smallest.name);
            if (size() > 1) {
                minHeap.set(1, lastitem);
                size --;
                contns.put(lastitem.name, 1);
                sink(1);
                return smallest.name;
            } else {
                size --;
                return smallest.name;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!(contains(item))) {
            throw new NoSuchElementException();
        } else {
            int itemposition = contns.get(item); //pretty sure that this is returning the item (key) and not the priority
            HeapItem currentitem = minHeap.get(itemposition);
            currentitem.pri = priority;
//            System.out.print("changed" + currentitem.name + "to priority" + currentitem.pri);
//            minHeap.set()
//            minHeap.add(itemposition, currentitem); // need to check to make sure it doesnt already exist
            swim(itemposition);
            int positionpostswim = contns.get(item);
            sink(positionpostswim);
        }
    }

    public void printHeap() {
        for(HeapItem h : minHeap) {
            System.out.println(h.name + " " + h.pri);
        }
    }

    public class HeapItem {
        T name;
        double pri;

        public HeapItem(T item, double priority) {
            name = item;
            pri = priority;
        }

        public T showName() {
            return name;
        }

        public double showpri() {
            return pri;
        }
    }
}
