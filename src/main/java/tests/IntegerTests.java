package tests;

import static mcunit.Assertions.assertEquals;
import mcunit.tags.*;

@Suite
public class IntegerTests {

    int x, y;

    @Before
    public void setUpContextForX() {
        this.x = 1;
    }

    @Before
    public void setUpContextForY() {
        this.y = 1;
    }

    @Test
    public void addTwoIntegers() {
        assertEquals(x + y, 2); // should pass
    }

    @Test
    public void subtractTwoIntegers() {
        assertEquals(x - y, 1); // Should fail (assertion error)
    }

    @Test
    public void throwAnException() {
        throw new RuntimeException("Unexpected Error!");
    }

}
