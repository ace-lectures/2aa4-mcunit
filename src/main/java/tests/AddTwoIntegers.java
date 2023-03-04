package tests;

import mcunit.TestCase;
import static mcunit.Assertions.assertEquals;

public class AddTwoIntegers extends TestCase {

    int x, y;

    @Override
    protected void setUp() {
        this.x = 1;
        this.y = 1;
    }

    @Override
    protected void test() {
        assertEquals(x + y, 2); // should pass
    }
}
