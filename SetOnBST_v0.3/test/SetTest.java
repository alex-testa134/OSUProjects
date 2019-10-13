import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    //Boundary Test Cases
    @Test
    public final void testBoundaryAdd() {
        Set<String> n = this.createFromArgsTest();
        Set<String> add = this.createFromArgsTest("A");
        Set<String> nExpected = this.createFromArgsRef("A");

        n.add(add);

        assertEquals(nExpected, n);
    }

    @Test
    public final void testBoundaryRemove() {
        Set<String> n = this.createFromArgsTest("A");
        Set<String> remove = this.createFromArgsTest("A");
        Set<String> nExpected = this.createFromArgsRef();
        Set<String> removedExpected = this.createFromArgsRef("A");

        Set<String> removed = n.remove(remove);

        assertEquals(nExpected, n);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testBoundaryRemoveAny() {
        Set<String> n = this.createFromArgsTest("A");
        Set<String> nExpected = this.createFromArgsRef();

        String removed = n.removeAny();

        assertEquals(nExpected, n);
        assertEquals("A", removed);
    }

    @Test
    public final void testBoudaryContainsTrue() {
        Set<String> n = this.createFromArgsTest("A");
        Set<String> nExpected = this.createFromArgsTest("A");

        boolean contains = n.contains("A");

        assertEquals(true, contains);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testBoudaryContainFalse() {
        Set<String> n = this.createFromArgsTest("A");
        Set<String> nExpected = this.createFromArgsTest("A");

        boolean contains = n.contains("B");

        assertEquals(false, contains);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testBoudarySize1() {
        Set<String> n = this.createFromArgsTest("A");
        Set<String> nExpected = this.createFromArgsTest("A");

        int size = n.size();

        assertEquals(1, size);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testBoudarySize0() {
        Set<String> n = this.createFromArgsTest();
        Set<String> nExpected = this.createFromArgsTest();

        int size = n.size();

        assertEquals(0, size);
        assertEquals(nExpected, n);
    }

    //Routine Test Cases
    public final void testRoutineAdd() {
        Set<String> n = this.createFromArgsTest("A");
        Set<String> add = this.createFromArgsTest("B");
        Set<String> nExpected = this.createFromArgsRef("A", "B");

        n.add(add);

        assertEquals(nExpected, n);
    }

    @Test
    public final void testRoutineRemove() {
        Set<String> n = this.createFromArgsTest("A");
        Set<String> remove = this.createFromArgsTest("A");
        Set<String> nExpected = this.createFromArgsRef();
        Set<String> removedExpected = this.createFromArgsRef("A");

        Set<String> removed = n.remove(remove);

        assertEquals(nExpected, n);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testRoutineRemoveAny() {
        Set<String> n = this.createFromArgsTest("A", "B");
        Set<String> nExpected = this.createFromArgsRef();

        String removed = n.removeAny();

        if (removed.equals("A")) {
            nExpected = this.createFromArgsRef("B");
        }

        else if (removed.equals("B")) {
            nExpected = this.createFromArgsRef("A");
        }

        assertEquals(nExpected, n);
    }

    @Test
    public final void testRoutineContainsTrue() {
        Set<String> n = this.createFromArgsTest("A", "B");
        Set<String> nExpected = this.createFromArgsTest("A", "B");

        boolean contains = n.contains("B");

        assertEquals(true, contains);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testRoutineContainFalse() {
        Set<String> n = this.createFromArgsTest("A", "B");
        Set<String> nExpected = this.createFromArgsTest("A", "B");

        boolean contains = n.contains("C");

        assertEquals(false, contains);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testRoutineSize() {
        Set<String> n = this.createFromArgsTest("A", "B");
        Set<String> nExpected = this.createFromArgsTest("A", "B");

        int size = n.size();

        assertEquals(2, size);
        assertEquals(nExpected, n);
    }

    //Challenging Test Cases

    public final void testChallengingAdd() {
        Set<String> n = this.createFromArgsTest("A", "B");
        Set<String> add = this.createFromArgsTest("C", "D");
        Set<String> nExpected = this.createFromArgsRef("A", "B", "C", "D");

        n.add(add);

        assertEquals(nExpected, n);
    }

    @Test
    public final void testChallengingRemove() {
        Set<String> n = this.createFromArgsTest("A", "B", "C");
        Set<String> remove = this.createFromArgsTest("B");
        Set<String> nExpected = this.createFromArgsRef("A", "C");
        Set<String> removedExpected = this.createFromArgsRef("B");

        Set<String> removed = n.remove(remove);

        assertEquals(nExpected, n);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testChallengingRemoveAny() {
        Set<String> n = this.createFromArgsTest("A", "B", "C");
        Set<String> nExpected = this.createFromArgsRef();

        String removed = n.removeAny();

        if (removed.equals("A")) {
            nExpected = this.createFromArgsRef("B", "C");
        }

        else if (removed.equals("B")) {
            nExpected = this.createFromArgsRef("A", "C");
        }

        else if (removed.equals("C")) {
            nExpected = this.createFromArgsRef("A", "B");
        }

        assertEquals(nExpected, n);
    }

    @Test
    public final void testChallengingContainsTrue() {
        Set<String> n = this.createFromArgsTest("A", "B", "C", "D");
        Set<String> nExpected = this.createFromArgsTest("A", "B", "C", "D");

        boolean contains = n.contains("D");

        assertEquals(true, contains);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testChallengingContainFalse() {
        Set<String> n = this.createFromArgsTest("A", "B", "C", "D");
        Set<String> nExpected = this.createFromArgsTest("A", "B", "C", "D");

        boolean contains = n.contains("E");

        assertEquals(false, contains);
        assertEquals(nExpected, n);
    }

    @Test
    public final void testChallengingSize() {
        Set<String> n = this.createFromArgsTest("A", "B", "C", "D", "E", "F");
        Set<String> nExpected = this.createFromArgsTest("A", "B", "C", "D", "E",
                "F");

        int size = n.size();

        assertEquals(6, size);
        assertEquals(nExpected, n);
    }

}
