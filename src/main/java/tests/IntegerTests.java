package tests;

import mcunit.TestCase;
import mcunit.TestSuite;

import static mcunit.Assertions.assertEquals;

public class IntegerTests extends TestSuite {

    public IntegerTests() {
        try {
            Class suite = IntegerTests.class;
            this.add(new TestCase(suite, suite.getMethod("addTwoIntegers")));
            this.add(new TestCase(suite, suite.getMethod("subtractTwoIntegers")));
            this.add(new TestCase(suite, suite.getMethod("throwAnException")));
        } catch (NoSuchMethodException nme) {
            throw new RuntimeException(nme);
        }
    }

    int x, y;

    public void setUp() {
        this.x = 1;
        this.y = 1;
    }

    public void addTwoIntegers() {
        assertEquals(x + y, 2); // should pass
    }

    public void subtractTwoIntegers() {
        assertEquals(x - y, 1); // Should fail (assertion error)
    }

    public void throwAnException() {
        throw new RuntimeException("Unexpected Error!");
    }

}
