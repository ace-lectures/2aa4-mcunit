package tests;

import mcunit.TestCase;

import static mcunit.Assertions.assertEquals;

public class SubtractTwoIntegers extends TestCase {

    int x, y;

    @Override
    protected void setUp() {
       this.x = 1;
       this.y = 1;
    }

    @Override
    protected void test() {
        assertEquals(x - y, 1); // Should fail (assertion error)
    }
}
