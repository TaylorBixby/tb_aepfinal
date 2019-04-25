package bearmaps;

/******************************************************************************
 *  Compilation:  javac StdRandom.java
 *  Execution:    java StdRandom
 *  Dependencies: StdOut.java
 *
 *  A library of static methods to generate pseudo-random numbers from
 *  different distributions (bernoulli, uniform, gaussian, discrete,
 *  and exponential). Also includes a method for shuffling an array.
 *
 *
 *  %  java StdRandom 5
 *  seed = 1316600602069
 *  59 16.81826  true 8.83954  0 
 *  32 91.32098  true 9.11026  0 
 *  35 10.11874  true 8.95396  3 
 *  92 32.88401  true 8.87089  0 
 *  72 92.55791  true 9.46241  0 
 *
 *  % java StdRandom 5
 *  seed = 1316600616575
 *  96 60.17070  true 8.72821  0 
 *  79 32.01607  true 8.58159  0 
 *  81 59.49065  true 9.10423  1 
 *  96 51.65818  true 9.02102  0 
 *  99 17.55771  true 8.99762  0 
 *
 *  % java StdRandom 5 1316600616575
 *  seed = 1316600616575
 *  96 60.17070  true 8.72821  0 
 *  79 32.01607  true 8.58159  0 
 *  81 59.49065  true 9.10423  1 
 *  96 51.65818  true 9.02102  0 
 *  99 17.55771  true 8.99762  0 
 *
 *
 *  Remark
 *  ------
 *    - Relies on randomness of nextDouble() method in java.util.Random
 *      to generate pseudo-random numbers in [0, 1).
 *
 *    - This library allows you to set and get the pseudo-random number seed.
 *
 *    - See http://www.honeylocust.com/RngPack/ for an industrial
 *      strength random number generator in Java.
 *
 ******************************************************************************/

import java.util.Random;

/**
 *  The {@code StdRandom} class provides static methods for generating
 *  random number from various discrete and continuous distributions, 
 *  including uniform, Bernoulli, geometric, Gaussian, exponential, Pareto,
 *  Poisson, and Cauchy. It also provides method for shuffling an
 *  array or subarray and generating random permutations.
 *  <p>
 *  By convention, all intervals are half open. For example,
 *  <code>uniform(-1.0, 1.0)</code> returns a random number between
 *  <code>-1.0</code> (inclusive) and <code>1.0</code> (exclusive).
 *  Similarly, <code>shuffle(a, lo, hi)</code> shuffles the <code>hi - lo</code>
 *  elements in the array <code>a[]</code>, starting at index <code>lo</code>
 *  (inclusive) and ending at index <code>hi</code> (exclusive).
 *  <p>
 *  For additional documentation,
 *  see <a href="https://introcs.cs.princeton.edu/22library">Section 2.2</a> of
 *  <i>Computer Science: An Interdisciplinary Approach</i>
 *  by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class StdRandom {

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    // don't instantiate
    private StdRandom() { }

    /**
     * Sets the seed of the pseudo-random number generator.
     * This method enables you to produce the same sequence of "random"
     * number for each execution of the program.
     * Ordinarily, you should call this method at most once per program.
     *
     * @param s the seed
     */
    public static void setSeed(long s) {
        seed   = s;
        random = new Random(seed);
    }

    /**
     * Returns the seed of the pseudo-random number generator.
     *
     * @return the seed
     */
    public static long getSeed() {
        return seed;
    }

    /**
     * Returns a random real number uniformly in [0, 1).
     *
     * @return a random real number uniformly in [0, 1)
     */
    public static double uniform() {
        return random.nextDouble();
    }

    /**
     * Returns a random integer uniformly in [0, n).
     * 
     * @param n number of possible integers
     * @return a random integer uniformly between 0 (inclusive) and {@code n} (exclusive)
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public static int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be positive: " + n);
        return random.nextInt(n);
    }


    /**
     * Returns a random long integer uniformly in [0, n).
     * 
     * @param n number of possible {@code long} integers
     * @return a random long integer uniformly between 0 (inclusive) and {@code n} (exclusive)
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public static long uniform(long n) {
        if (n <= 0L) throw new IllegalArgumentException("argument must be positive: " + n);

        // https://docs.oracle.com/javase/8/docs/api/java/util/Random.html#longs-long-long-long-
        long r = random.nextLong();
        long m = n - 1;

        // power of two
        if ((n & m) == 0L) {
            return r & m;
        }

        // reject over-represented candidates
        long u = r >>> 1;
        while (u + m - (r = u % n) < 0L) {
            u = random.nextLong() >>> 1;
        }
        return r;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  STATIC METHODS BELOW RELY ON JAVA.UTIL.RANDOM ONLY INDIRECTLY VIA
    //  THE STATIC METHODS ABOVE.
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a random real number uniformly in [0, 1).
     * 
     * @return     a random real number uniformly in [0, 1)
     * @deprecated Replaced by {@link #uniform()}.
     */
    @Deprecated
    public static double random() {
        return uniform();
    }

    /**
     * Returns a random integer uniformly in [a, b).
     * 
     * @param  a the left endpoint
     * @param  b the right endpoint
     * @return a random integer uniformly in [a, b)
     * @throws IllegalArgumentException if {@code b <= a}
     * @throws IllegalArgumentException if {@code b - a >= Integer.MAX_VALUE}
     */
    public static int uniform(int a, int b) {
        if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(b - a);
    }

    /**
     * Returns a random real number uniformly in [a, b).
     * 
     * @param  a the left endpoint
     * @param  b the right endpoint
     * @return a random real number uniformly in [a, b)
     * @throws IllegalArgumentException unless {@code a < b}
     */
    public static double uniform(double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform() * (b-a);
    }


    /**
     * Returns a random real number from an exponential distribution
     * with rate &lambda;.
     * 
     * @param  lambda the rate of the exponential distribution
     * @return a random real number from an exponential distribution with
     *         rate {@code lambda}
     * @throws IllegalArgumentException unless {@code lambda > 0.0}
     */
    public static double exp(double lambda) {
        if (!(lambda > 0.0))
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        return -Math.log(1 - uniform()) / lambda;
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws IllegalArgumentException if {@code a} is {@code null}
     */
    public static void shuffle(Object[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws IllegalArgumentException if {@code a} is {@code null}
     */
    public static void shuffle(double[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws IllegalArgumentException if {@code a} is {@code null}
     */
    public static void shuffle(int[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified array in uniformly random order.
     *
     * @param  a the array to shuffle
     * @throws IllegalArgumentException if {@code a} is {@code null}
     */
    public static void shuffle(char[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n-i);     // between i and n-1
            char temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param  a the array to shuffle
     * @param  lo the left endpoint (inclusive)
     * @param  hi the right endpoint (exclusive)
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     * 
     */
    public static void shuffle(Object[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(hi-i);     // between i and hi-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param  a the array to shuffle
     * @param  lo the left endpoint (inclusive)
     * @param  hi the right endpoint (exclusive)
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static void shuffle(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(hi-i);     // between i and hi-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Rearranges the elements of the specified subarray in uniformly random order.
     *
     * @param  a the array to shuffle
     * @param  lo the left endpoint (inclusive)
     * @param  hi the right endpoint (exclusive)
     * @throws IllegalArgumentException if {@code a} is {@code null}
     * @throws IllegalArgumentException unless {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static void shuffle(int[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(hi-i);     // between i and hi-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Returns a uniformly random permutation of <em>n</em> elements.
     *
     * @param  n number of elements
     * @throws IllegalArgumentException if {@code n} is negative
     * @return an array of length {@code n} that is a uniformly random permutation
     *         of {@code 0}, {@code 1}, ..., {@code n-1}
     */
    public static int[] permutation(int n) {
        if (n < 0) throw new IllegalArgumentException("argument is negative");
        int[] perm = new int[n];
        for (int i = 0; i < n; i++)
            perm[i] = i;
        shuffle(perm);
        return perm;
    }

    /**
     * Returns a uniformly random permutation of <em>k</em> of <em>n</em> elements.
     *
     * @param  n number of elements
     * @param  k number of elements to select
     * @throws IllegalArgumentException if {@code n} is negative
     * @throws IllegalArgumentException unless {@code 0 <= k <= n}
     * @return an array of length {@code k} that is a uniformly random permutation
     *         of {@code k} of the elements from {@code 0}, {@code 1}, ..., {@code n-1}
     */
    public static int[] permutation(int n, int k) {
        if (n < 0) throw new IllegalArgumentException("argument is negative");
        if (k < 0 || k > n) throw new IllegalArgumentException("k must be between 0 and n");
        int[] perm = new int[k];
        for (int i = 0; i < k; i++) {
            int r = uniform(i+1);    // between 0 and i
            perm[i] = perm[r];
            perm[r] = i;
        }
        for (int i = k; i < n; i++) {
            int r = uniform(i+1);    // between 0 and i
            if (r < k) perm[r] = i;
        }
        return perm;
    }

    // throw an IllegalArgumentException if x is null
    // (x can be of type Object[], double[], int[], ...)
    private static void validateNotNull(Object x) {
        if (x == null) {
            throw new IllegalArgumentException("argument is null");
        }
    }

    // throw an exception unless 0 <= lo <= hi <= length
    private static void validateSubarrayIndices(int lo, int hi, int length) {
        if (lo < 0 || hi > length || lo > hi) {
            throw new IllegalArgumentException("subarray indices out of bounds: [" + lo + ", " + hi + ")");
        }
    }

    /**
     * Unit tests the methods in this class.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        if (args.length == 2) StdRandom.setSeed(Long.parseLong(args[1]));
        double[] probabilities = { 0.5, 0.3, 0.1, 0.1 };
        int[] frequencies = { 5, 3, 1, 1 };
        String[] a = "A B C D E F G".split(" ");

        System.out.println("seed = " + StdRandom.getSeed());
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d ",   uniform(100));
            System.out.printf("%8.5f ", uniform(10.0, 99.0));
            System.out.printf("%11d ",  uniform(100000000000L));
            StdRandom.shuffle(a);
            for (String s : a)
                System.out.print(s);
            System.out.println();
        }
    }

}
