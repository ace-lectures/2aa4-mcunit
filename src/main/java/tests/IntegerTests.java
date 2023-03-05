package tests;

import mcunit.TestCase;
import mcunit.TestSuite;

import static mcunit.Assertions.assertEquals;

public class IntegerTests extends TestSuite {

    public IntegerTests() {
        this.add(new AddTwoIntegers());
        this.add(new SubtractTwoIntegers());
        this.add(new ThrowAnException());
    }

    private static class AddTwoIntegers extends TestCase {

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

    private static class SubtractTwoIntegers extends TestCase {

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

    private static class ThrowAnException extends TestCase {

        @Override
        protected void test() {
            throw new RuntimeException("Unexpected Error!");
        }
    }

}
