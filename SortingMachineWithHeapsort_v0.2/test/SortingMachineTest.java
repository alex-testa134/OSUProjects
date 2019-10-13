import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures
     *
     *          <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     *          </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures
     *
     *          <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     *          </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddBoundary() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> nExpected = this.createFromArgsRef(ORDER, true,
                "A");
        n.add("A");
        assertEquals(nExpected, n);
    }

    @Test
    public final void testChangeToExtractionModeBoundary() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true);

        n.changeToExtractionMode();
        assertEquals(false, n.isInInsertionMode());
    }

    @Test
    public final void testRemoveFirstBoundary() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A");
        SortingMachine<String> nExpected = this.createFromArgsRef(ORDER, true);
        n.changeToExtractionMode();
        nExpected.changeToExtractionMode();
        String first = n.removeFirst();
        assertEquals(nExpected, n);
        assertEquals(first, "A");
    }

    @Test
    public final void testIsInInsertionModeTrue1() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true);
        assertEquals(true, n.isInInsertionMode());
    }

    @Test
    public final void testIsInInsertionModeTrue2() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true);
        n.changeToExtractionMode();
        assertEquals(false, n.isInInsertionMode());
    }

    @Test
    public final void testOrderBoundary() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A",
                "C", "B");
        SortingMachine<String> nExpected = this.createFromArgsRef(ORDER, true,
                "A", "B", "C");
        Comparator<String> order = n.order();
        Comparator<String> orderRef = nExpected.order();

        assertEquals(order, orderRef);
    }

    @Test
    public final void testSizeBoundary() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true);

        assertEquals(n.size(), 0);
    }

    //Routine Test Cases

    @Test
    public final void testAddRoutine() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A");
        SortingMachine<String> nExpected = this.createFromArgsRef(ORDER, true,
                "A", "B");
        n.add("B");
        assertEquals(nExpected, n);
    }

    @Test
    public final void testChangeToExtractionModeRoutine() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A");

        n.changeToExtractionMode();
        assertEquals(false, n.isInInsertionMode());
    }

    @Test
    public final void testRemoveFirstRoutine() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A",
                "B");
        SortingMachine<String> nExpected = this.createFromArgsRef(ORDER, true,
                "B");
        n.changeToExtractionMode();
        nExpected.changeToExtractionMode();
        String first = n.removeFirst();
        assertEquals(nExpected, n);
        assertEquals(first, "A");
    }

    @Test
    public final void testOrderRoutine() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A",
                "C", "B");
        SortingMachine<String> nExpected = this.createFromArgsRef(ORDER, true,
                "B", "C");
        Comparator<String> order = n.order();
        Comparator<String> orderRef = nExpected.order();

        assertEquals(order, orderRef);
    }

    @Test
    public final void testSizeRoutine() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A",
                "B");

        assertEquals(n.size(), 2);
    }

    //Challenging Test Cases

    @Test
    public final void testAddChallenging() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A",
                "B", "C");
        SortingMachine<String> nExpected = this.createFromArgsRef(ORDER, true,
                "A", "B", "C", "D");
        n.add("D");
        assertEquals(nExpected, n);
    }

    @Test
    public final void testRemoveFirstChallenging() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, false, "A",
                "B", "C");
        SortingMachine<String> nExpected = this.createFromArgsRef(ORDER, true,
                "B", "C");
        n.changeToExtractionMode();
        nExpected.changeToExtractionMode();
        String first = n.removeFirst();
        assertEquals(nExpected, n);
        assertEquals(first, "A");
    }

    @Test
    public final void testIsInInsertionModeTrue3() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A");
        assertEquals(true, n.isInInsertionMode());
    }

    @Test
    public final void testIsInInsertionModeTrue4() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A");
        n.changeToExtractionMode();
        assertEquals(false, n.isInInsertionMode());
    }

    @Test
    public final void testOrderChallenging() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A",
                "C", "B", "D", "F");
        SortingMachine<String> nExpected = this.createFromArgsRef(ORDER, true,
                "B", "C", "D", "F");
        Comparator<String> order = n.order();
        Comparator<String> orderRef = nExpected.order();

        assertEquals(order, orderRef);
    }

    @Test
    public final void testSizeChallenging() {
        SortingMachine<String> n = this.createFromArgsTest(ORDER, true, "A",
                "B", "C", "D", "E", "F");

        assertEquals(6, n.size());
    }

}
