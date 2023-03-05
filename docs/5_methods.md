# Step #5: Methods instead of internal classes

## 5.1: Getting rid of inheritance in TestCase

1. Update the `TestCase` class to make it a self-contained (and final) class. 
    - A Test case is a method to be invoked in a class.
    - We use intercession to create objects on the fly (tests contexts), and to call test method on such contexts
    - the intercession (reflexive) part of the code is _hidden_ inside the test case. No one cares, so no one knows.
    - Reflexivity is a way to bypass the type system, and the compiler. **With great power comes great responsibility**, so now we have to deal wit a lot of annoying exceptions (illegal access, security, no such method, ...). Again, this need to stay encapsulated in the test case.

```java
public final class TestCase implements Test {

    private static final String SETUP = "setUp";
    private static final String TEARDOWN = "tearDown";

    private final Method testMethod;
    private final Class klass;

    public TestCase(Class klass, Method testMethod) {
        this.testMethod = testMethod;
        this.klass = klass;
    }

    @Override
    public final void run(TestReport collector) {
        String title = klass.getCanonicalName() + "::" + testMethod.getName();
        TestResult result = new TestResult(title);
        Object context = null;
        try {
            context =  klass.getDeclaredConstructor().newInstance();
            runSetUp(context);
            runTest(context);
            result.record(STATUS.PASSED);
        } catch(AssertionError ae) {
            result.record(STATUS.FAILED);
        } catch (Exception e) {
            result.record(STATUS.ERRORED);
        } finally {
            try {
                runTearDown(context);
            } catch (Exception e) {
                result.record(STATUS.ERRORED);
            }
        }
        collector.collect(result);
    }

    private void runSetUp(Object context)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if(getMethodByName(SETUP).isPresent()) {
            Method setup = getMethodByName(SETUP).get();
            setup.invoke(context);
        }
    }

    private void runTearDown(Object context)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if(getMethodByName(TEARDOWN).isPresent()) {
            Method setup = getMethodByName(TEARDOWN).get();
            setup.invoke(context);
        }
    }

    private void runTest(Object context)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            this.testMethod.invoke(context);

    }

    private Optional<Method> getMethodByName(String methodName) {
        try {
            Method result = this.klass.getMethod(methodName);
            return Optional.of(result);
        } catch(NoSuchMethodException nme) {
            return Optional.empty();
        }
    }
}
```

2. Refactor `IntegerTests` to now use methods instead of internal classes.

```java
public class IntegerTests extends TestSuite {

    public IntegerTests() {
        try {
            Class suite = IntegerTests.class;
            this.add(new TestCase(suite, suite.getMethod("addTwoIntegers")));
            this.add(new TestCase(suite, suite.getMethod("subtractTwoIntegers")));
            this.add(new TestCase(suite, suite.getMethod("throwAnException")));
        } catch (NoSuchMethodException nme) {
            throw new RuntimeException(nme);
        }
    }

    int x, y;

    public void setUp() {
        this.x = 1;
        this.y = 1;
    }

    public void addTwoIntegers() {
        assertEquals(x + y, 2); // should pass
    }

    public void subtractTwoIntegers() {
        assertEquals(x - y, 1); // Should fail (assertion error)
    }

    public void throwAnException() {
        throw new RuntimeException("Unexpected Error!");
    }

}
```

3. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java    
# 2AA4 - McUnit Demo
# [!] tests.IntegerTests::throwAnException
# [X] tests.IntegerTests::addTwoIntegers
# [!] tests.IntegerTests::subtractTwoIntegers
Summary: {[X]=1, [!]=2}
mosser@azrael 2aa4-mcunit % 
```

**WE HAVE A REGRESSION**. The refactoring is not finished yet, as the behavior of the McUnit system is not isofunctional with the former one. We have introduced a bug.

## 5.2: Handling Exceptions properly

1. Our execution code in the `TestCase` class assumes that we throw an `AssertionError` exception when a test fails. But now, we are not directly throwing it, as it is the reflexive call that will have thrown such an exception.
    - We need to dig into the exception that might have happened inside the target of the call.
2. Update the `TestCase` class to implement such a digging.

```java
public final void run(TestReport collector) {
    String title = klass.getCanonicalName() + "::" + testMethod.getName();
    TestResult result = new TestResult(title);
    Object context = null;
    try {
        context =  klass.getDeclaredConstructor().newInstance();
        runSetUp(context);
        runTest(context);
        result.record(STATUS.PASSED);
    } catch(InvocationTargetException ite) {
        try { // What happened inside the test method?
            throw ite.getTargetException();
        } catch (AssertionError ae) {
            result.record(STATUS.FAILED);
        } catch(Throwable e) {
            result.record(STATUS.ERRORED);
        }
    } catch (Exception e) {
        result.record(STATUS.ERRORED);
    } finally {
        try {
            runTearDown(context);
        } catch (Exception e) {
            result.record(STATUS.ERRORED);
        }
    }
    collector.collect(result);
}
```

3. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java    
# 2AA4 - McUnit Demo
# [!] tests.IntegerTests::throwAnException
# [X] tests.IntegerTests::addTwoIntegers
# [ ] tests.IntegerTests::subtractTwoIntegers
Summary: {[!]=1, [X]=1, [ ]=1}
```

Bug fixed! We're now back to normal.

4. Record to VCS.

```
mosser@azrael 2aa4-mcunit % git add src/main/java 
mosser@azrael 2aa4-mcunit % git commit -m "supporting methods as test cases"
[main 341275a] supporting methods as test cases
 2 files changed, 79 insertions(+), 45 deletions(-)
mosser@azrael 2aa4-mcunit % 
```

## 5.3 Release and publish

```
mosser@azrael 2aa4-mcunit % git tag step_5
mosser@azrael 2aa4-mcunit % git push --tags
mosser@azrael 2aa4-mcunit % git push
mosser@azrael 2aa4-mcunit % 
```
