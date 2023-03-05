package mcunit;

import java.lang.reflect.Method;

public class TestFactory {

    private static final String TEST_PREFIX = "test_";

    public Test build(Class suite) {
        TestSuite result = new TestSuite();
        for(Method candidate: suite.getMethods()) {
            if(candidate.getName().startsWith(TEST_PREFIX)) {
                result.add(new TestCase(suite, candidate));
            }
        }
        return result;
    }

}
