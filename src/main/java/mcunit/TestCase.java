package mcunit;

public abstract class TestCase implements Test {

    @Override
    public final void run(TestReport collector) {
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
        collector.collect(result);
    }

    protected void setUp() { }

    protected void tearDown() { }

    protected abstract void test();

}
