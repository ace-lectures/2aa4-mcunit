package mcunit;

public abstract class TestCase {

    public final TestResult run() {
        TestResult result = new TestResult(this.getClass().getCanonicalName());
        try {
            setUp();
            test(); // <<-- Design Pattern: Template method
            result.record(STATUS.PASSED);
        } catch(AssertionError ae) {
            result.record(STATUS.FAILED);
        } catch (Exception e) {
            result.record(STATUS.ERRORED);
        } finally {
            tearDown();
        }
        return result;
    }

    protected void setUp() { }

    protected void tearDown() { }

    protected abstract void test();

}
