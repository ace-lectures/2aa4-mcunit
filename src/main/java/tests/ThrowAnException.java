package tests;

import mcunit.TestCase;

import static mcunit.Assertions.assertEquals;

public class ThrowAnException extends TestCase {

    @Override
    protected void test() {
        throw new RuntimeException("Unexpected Error!");
    }
}
