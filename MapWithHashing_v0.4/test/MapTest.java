import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires
     *
     *           <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     *           </pre>
     *
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires
     *
     *           <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     *           </pre>
     *
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    //Boundary Test Cases

    @Test
    public final void testValue0() {
        Map<String, String> test = this.createFromArgsTest("AA", "0");
        String nExpected = "0";
        String n = test.value("AA");

        assertEquals(nExpected, n);

    }

    @Test
    public final void testAdd0() {
        Map<String, String> n = this.createFromArgsTest();
        Map<String, String> nExpected = this.createFromArgsRef("AA", "0");

        n.add("AA", "0");

        assertEquals(nExpected, n);
    }

    @Test
    public final void testRemove1() {
        Map<String, String> n = this.createFromArgsTest("AA", "12");
        Map<String, String> nExpected = this.createFromArgsRef();

        Map.Pair<String, String> test = n.remove("AA");
        String testKey = "AA";
        String testValue = "23";

        assertEquals(nExpected, n);
        assertEquals(testKey, test.key());
        assertEquals(testValue, test.value());
    }

    @Test
    public final void testRemove0() {
        Map<String, String> n = this.createFromArgsTest("AA", "0");
        Map<String, String> nExpected = this.createFromArgsRef();

        Map.Pair<String, String> test = n.remove("AA");
        String testKey = "AA";
        String testValue = "0";

        assertEquals(nExpected, n);
        assertEquals(testKey, test.key());
        assertEquals(testValue, test.value());
    }

    @Test
    public final void testSize0() {
        Map<String, String> n = this.createFromArgsTest();

        int nSize = n.size();
        int nSizeExpected = 0;

        assertEquals(nSizeExpected, nSize);
    }

    @Test
    public final void testSize1() {
        Map<String, String> n = this.createFromArgsTest("AA", "12");

        int nSize = n.size();
        int nSizeExpected = 1;

        assertEquals(nSizeExpected, nSize);
    }

    @Test
    public final void testRemoveAny0() {
        Map<String, String> n = this.createFromArgsTest("AA", "0");
        Map<String, String> nExpected = this.createFromArgsRef();

        Map.Pair<String, String> test = n.removeAny();
        String testKey = "AA";
        String testValue = "0";

        assertEquals(nExpected, n);
        assertEquals(testKey, test.key());
        assertEquals(testValue, test.value());
    }

    @Test
    public final void hasKeyTrue() {
        Map<String, String> n = this.createFromArgsTest("AA", "0");

        boolean testHasKey = n.hasKey("AA");

        assertEquals(true, testHasKey);
    }

    @Test
    public final void hasKeyFalse() {
        Map<String, String> n = this.createFromArgsTest("AA", "0");

        boolean testHasKey = n.hasKey("BC");

        assertEquals(false, testHasKey);
    }

    @Test
    public final void hasKeyFalse2() {
        Map<String, String> n = this.createFromArgsTest();

        boolean testHasKey = n.hasKey("BC");

        assertEquals(false, testHasKey);
    }

    // Routine Test Cases

    @Test
    public final void testValueAB() {
        Map<String, String> test = this.createFromArgsTest("AA", "Apple Pie",
                "AB", "Cherry Pie");
        String nExpected = "Cherry Pie";
        String n = test.value("AB");

        assertEquals(nExpected, n);

    }

    @Test
    public final void testAddAAandAB() {
        Map<String, String> n = this.createFromArgsTest();
        Map<String, String> nExpected = this.createFromArgsRef("AA", "0", "AB",
                "2");

        n.add("AA", "0");
        n.add("AB", "2");

        assertEquals(nExpected, n);
    }

    @Test
    public final void testRemoveAB() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452");
        Map<String, String> nExpected = this.createFromArgsRef("AA", "643",
                "AC", "3452");

        Map.Pair<String, String> test = n.remove("AA");
        String testKey = "AB";
        String testValue = "123";

        assertEquals(nExpected, n);
        assertEquals(testKey, test.key());
        assertEquals(testValue, test.value());
    }

    @Test
    public final void testSize3() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452");

        int nSize = n.size();
        int nSizeExpected = 3;

        assertEquals(nSizeExpected, nSize);
    }

    @Test
    public final void testRemoveAny() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452");

        String testKey = "12";
        String testValue = "45";

        Map.Pair<String, String> test = n.removeAny();

        if (test.key() == "AA") {
            testKey = "AA";
            testValue = "643";
        }

        else if (test.key() == "AB") {
            testKey = "AB";
            testValue = "123";
        }

        else if (test.key() == "AC") {
            testKey = "AC";
            testValue = "3452";
        }

        assertEquals(testKey, test.key());
        assertEquals(testValue, test.value());
    }

    @Test
    public final void hasKeyABTrue() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452");

        boolean testHasKey = n.hasKey("AB");

        assertEquals(true, testHasKey);
    }

    @Test
    public final void hasKeyCDFalse() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452");

        boolean testHasKey = n.hasKey("CD");

        assertEquals(false, testHasKey);
    }

    //Challenging Test Cases

    @Test
    public final void testValueBA() {
        Map<String, String> test = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452", "BA", "356", "BB", "62343", "BC", "2352");
        String nExpected = "356";
        String n = test.value("BA");

        assertEquals(nExpected, n);

    }

    @Test
    public final void testAddAAandABandACandBA() {
        Map<String, String> n = this.createFromArgsTest();
        Map<String, String> nExpected = this.createFromArgsRef("AA", "643",
                "AB", "123", "AC", "3452", "BA", "356");

        n.add("AA", "643");
        n.add("AB", "123");
        n.add("AC", "3452");
        n.add("BA", "356");

        assertEquals(nExpected, n);
    }

    @Test
    public final void testRemoveBB() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452", "BA", "356", "BB", "62343", "BC", "2352");
        Map<String, String> nExpected = this.createFromArgsRef("AA", "643",
                "AB", "123", "AC", "3452", "BA", "356", "BC", "2352");

        Map.Pair<String, String> test = n.remove("BB");
        String testKey = "BB";
        String testValue = "62343";

        assertEquals(nExpected, n);
        assertEquals(testKey, test.key());
        assertEquals(testValue, test.value());
    }

    @Test
    public final void testSize10() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452", "BA", "356", "BB", "62343", "BC", "2352",
                "BD", "561", "CA", "6124", "CB", "567", "CC", "12");

        int nSize = n.size();
        int nSizeExpected = 10;

        assertEquals(nSizeExpected, nSize);
    }

    @Test
    public final void testRemoveAnyLarge() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452", "BA", "356", "BB", "64323");

        String testKey = "1553";
        String testValue = "23";

        Map.Pair<String, String> test = n.removeAny();

        if (test.key() == "AA") {
            testKey = "AA";
            testValue = "643";
        }

        else if (test.key() == "AB") {
            testKey = "AB";
            testValue = "123";
        }

        else if (test.key() == "AC") {
            testKey = "AC";
            testValue = "3452";
        }

        else if (test.key() == "BA") {
            testKey = "BA";
            testValue = "356";
        }

        else if (test.key() == "BB") {
            testKey = "BB";
            testValue = "62343";
        }

        assertEquals(testKey, test.key());
        assertEquals(testValue, test.value());
    }

    @Test
    public final void hasKeyBATrue() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452", "BA", "356", "BB", "62343", "BC", "2352");

        boolean testHasKey = n.hasKey("AC");

        assertEquals(true, testHasKey);
    }

    @Test
    public final void hasKeyDCFalse() {
        Map<String, String> n = this.createFromArgsTest("AA", "643", "AB",
                "123", "AC", "3452", "BA", "356", "BB", "62343", "BC", "2352");

        boolean testHasKey = n.hasKey("DC");

        assertEquals(false, testHasKey);
    }
}