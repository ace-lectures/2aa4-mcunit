package mcunit;

import static mcunit.Assertions.assertEquals;

public abstract class TestCase {

    public final void run() {
        System.out.println("## Case " + this.getClass().getCanonicalName());
        try {
            test(); // <<-- Design Pattern: Template method
            System.out.println("PASSED");
        } catch(AssertionError ae) {
            System.out.println("FAILED");
        }
    }

    protected abstract void test();

}
