import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Alex Testa and Devon Chen
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors

    // Boundary Test Cases

    @Test
    public final void testBoundaryMultiplyBy10() {
        int k = 9;
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(9);
        n.multiplyBy10(k);

        assertEquals(nExpected, n);
    }

    @Test
    public final void testBoundaryDivideBy10() {
        int ret = 1;
        int rem;
        NaturalNumber n = this.constructorTest(11);
        rem = n.divideBy10();

        assertEquals(ret, rem);
    }

    @Test
    public final void testBoundaryIsZeroFalse() {
        boolean test;
        NaturalNumber n = this.constructorTest(1);
        test = n.isZero();

        assertEquals(false, test);
    }

    @Test
    public final void testBoundaryIsZeroTrue() {
        boolean test;
        NaturalNumber n = this.constructorTest(0);
        test = n.isZero();

        assertEquals(true, test);
    }

    //Routine Test Cases

    @Test
    public final void testRoutineMultiplyBy10() {
        int k = 134;
        NaturalNumber n = this.constructorTest(21);
        NaturalNumber nExpected = this.constructorRef(21134);
        n.multiplyBy10(k);

        assertEquals(nExpected, n);
    }

    @Test
    public final void testRoutineDivideBy10() {
        int ret = 5;
        int rem;
        NaturalNumber n = this.constructorTest(155);
        rem = n.divideBy10();

        assertEquals(ret, rem);
    }

    @Test
    public final void testRoutineIsZero() {
        boolean test;
        NaturalNumber n = this.constructorTest(32412);
        test = n.isZero();

        assertEquals(false, test);
    }

    //Challenging Test Cases

    @Test
    public final void testChallengingMultiplyBy10() {
        int k = 9;
        String max = "21474836479";
        NaturalNumber n = this.constructorTest(2147483647);
        NaturalNumber nExpected = this.constructorRef(max);
        n.multiplyBy10(k);

        assertEquals(nExpected, n);
    }

    @Test
    public final void testChallengingDivideBy10() {
        int ret = 3;
        int rem;
        NaturalNumber n = this.constructorTest(632453);
        rem = n.divideBy10();

        assertEquals(ret, rem);

    }

    @Test
    public final void testChallengingIsZero() {
        boolean test;
        NaturalNumber n = this.constructorTest(2147483647);
        test = n.isZero();

        assertEquals(false, test);
    }

    @Test
    public final void testChallengingIsZero2() {
        boolean test;
        NaturalNumber n = this.constructorTest(-2147483647);
        test = n.isZero();

        assertEquals(false, test);
    }
}
