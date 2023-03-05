# Step #7: Using Annotations to tag  tests

## 7.1: Creating our annotations

1. Create a package named `tags`, nexted in `mcunit`.
2. In this package, create an annotation named `Test`

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {}
```

3. In this package, create an annotation named `Before`

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {}
```

4. In this package, create an annotation named `After`

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface After {}
```

2. In this package, create an annotation named `Suite`

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Suite {}
```

## 7.2: Using the annotation in our test code

1. Update the `IntegerTests` class to use the newly created anotations instead of the naming conventions.

```java
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
```

## 7.3: Modifying TestCase pre and post processing


1. Edit the `TestCase` class to leverage the annotations
    -  We now look for annotated methods instead of precise `setUp()` and `tearDown` ones.

```java
public final class TestCase implements Test {

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
        } catch(InvocationTargetException ite) {
            try {
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

    private void runSetUp(Object context)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for(Method candidate: klass.getMethods()) {
            if (candidate.isAnnotationPresent(Before.class)) {
                candidate.invoke(context);
            }
        }
    }

    private void runTearDown(Object context)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for(Method candidate: klass.getMethods()) {
            if (candidate.isAnnotationPresent(After.class)) {
                candidate.invoke(context);
            }
        }
    }

    private void runTest(Object context)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            this.testMethod.invoke(context);

    }

}
```

## 7.4: Update the factory to look for annotations

1. We update the factory code to rely on annotations instead of name prefix

```java
public class TestFactory {

    private static final String TEST_PREFIX = "test_";

    public Test build(Class suite) {
        TestSuite result = new TestSuite();
        for(Method candidate: suite.getMethods()) {
            if(candidate.isAnnotationPresent(mcunit.tags.Test.class)) {
                result.add(new TestCase(suite, candidate));
            }
        }
        return result;
    }

}
```

## Compile, execute, record, release and publish!

1. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java    
# 2AA4 - McUnit Demo
# [ ] tests.IntegerTests::subtractTwoIntegers
# [X] tests.IntegerTests::addTwoIntegers
# [!] tests.IntegerTests::throwAnException
Summary: {[!]=1, [X]=1, [ ]=1}
mosser@azrael 2aa4-mcunit % 
```

2. Record into VCS

```
mosser@azrael 2aa4-mcunit % git add src/main/java
mosser@azrael 2aa4-mcunit % git commit -m "supporting annotations instead of name conventions"
[main 5ce5a5d] supporting annotations instead of name conventions
 7 files changed, 70 insertions(+), 25 deletions(-)
 create mode 100644 src/main/java/mcunit/tags/After.java
 create mode 100644 src/main/java/mcunit/tags/Before.java
 create mode 100644 src/main/java/mcunit/tags/Suite.java
 create mode 100644 src/main/java/mcunit/tags/Test.java
```

3. Release and publish

```
mosser@azrael 2aa4-mcunit % git tag step_7
mosser@azrael 2aa4-mcunit % git push --tags
mosser@azrael 2aa4-mcunit % git push
mosser@azrael 2aa4-mcunit % 
```
