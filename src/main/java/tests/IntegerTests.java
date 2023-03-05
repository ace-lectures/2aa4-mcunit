package tests;

import mcunit.TestCase;
import mcunit.TestSuite;

import static mcunit.Assertions.assertEquals;

public class IntegerTests {

    int x, y;

    public void setUp() {
        this.x = 1;
        this.y = 1;
    }

    public void test_addTwoIntegers() {
        assertEquals(x + y, 2); // should pass
    }

    public void test_subtractTwoIntegers() {
        assertEquals(x - y, 1); // Should fail (assertion error)
    }

    public void test_throwAnException() {
        throw new RuntimeException("Unexpected Error!");
    }

}
