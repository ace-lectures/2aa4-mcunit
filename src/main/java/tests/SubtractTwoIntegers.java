package tests;

import mcunit.TestCase;

import static mcunit.Assertions.assertEquals;

public class SubtractTwoIntegers extends TestCase {

    @Override
    protected void test() {
        int y = 1;
        int x = 1;
        assertEquals(x - y, 1); // Should fail (assertion error)
    }
}
