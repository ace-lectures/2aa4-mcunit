# Step #4: Organizing Test Suites

## 3.0: Identifying design flaw in the way we collect results

- A `TestCase` _returns_ a `TestResult` when executed. Thus, it is impossible to consider a single test and a group of test with an homogenous interface.
- We have a polymorphism problem. We would have to distinguish atomic test cases, and suites made of multiple cases.
- By collecting the results from "outside" the test, we prevent ourselves to consider tests in an homogeneous way.

```java
TestReport report = new TestReport();
report.collect(new AddTwoIntegers().run());
report.collect(new SubtractTwoIntegers().run());
report.collect(new ThrowAnException().run());
```

## 3.1: Inversing responsibility between TestCase and TestReport

1. Rationale: We'll deleguate to the individual tests the responsibility of recording their execution in a report.
2. Create a `Test` interface that make this contract explicit.

```java
public interface Test {

    void run(TestReport collector);

}
```

2. Refactor the `TestCase` class to realizes this interface, and change the signature of the `run` method

```java
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
```

3. Update the `Main` class to reflect the change

```java
public class Main {
    
    public static void main(String[] args) {
        System.out.println("# 2AA4 - McUnit Demo");

        TestReport report = new TestReport();
        new AddTwoIntegers().run(report);
        new SubtractTwoIntegers().run(report);
        new ThrowAnException().run(report);

        System.out.println(report);
    }

}
```

4. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package                         
mosser@azrael 2aa4-mcunit % mvn -q exec:java                             
# 2AA4 - McUnit Demo
# [X] tests.AddTwoIntegers
# [ ] tests.SubtractTwoIntegers
# [!] tests.ThrowAnException
Summary: {[X]=1, [ ]=1, [!]=1}
mosser@azrael 2aa4-mcunit %
```

5. Record in VCS

```
git add src/main/java/Main.java src/main/java/mcunit/Test.java src/main/java/mcunit/TestCase.java 
mosser@azrael 2aa4-mcunit % git commit -m "refactor TestCase/TestReport usage"                                               
[main f868083] refactor TestCase/TestReport usage
 4 files changed, 16 insertions(+), 6 deletions(-)
 create mode 100644 src/main/java/mcunit/Test.java
 create mode 100644 src/main/java/mcunit/TestSuite.java
mosser@azrael 2aa4-mcunit % 
```

## 3.2: Creating the TestSuite class

1. Create a class named `TestSuite` in the `mcunit` package

```java
public class TestSuite implements Test {

    private final Set<Test> contents = new HashSet<>();
    
    @Override
    public final void run(TestReport collector) {
        for(Test t: contents) {
            t.run(collector);
        }
    }

    public final void add(Test t) {
        this.contents.add(t);
    }
}
```

## 3.3: Creating a TesSuite: IntegerTests

1. Create a class named `IntegerTests` in the `tests` package.
  - The class contains all the previous tests as private hidden classes.

```java
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
```

2. Delete the previous visible test classes (`AddTwoIntegers`, `SubtractTwoIntegers`, `ThrowAnException`)
3. Update the `Main` class to now rely on `IntegerTests` only, as  `Test`.

```java
public class Main {
    
    public static void main(String[] args) {
        System.out.println("# 2AA4 - McUnit Demo");

        TestReport report = new TestReport();
        Test integerTests = new IntegerTests();
        integerTests.run(report);
        System.out.println(report);
    }

}
```

4. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java    
# 2AA4 - McUnit Demo
# [ ] tests.IntegerTests.SubtractTwoIntegers
# [!] tests.IntegerTests.ThrowAnException
# [X] tests.IntegerTests.AddTwoIntegers
Summary: {[ ]=1, [!]=1, [X]=1}
mosser@azrael 2aa4-mcunit % 
```

5. Record into VCS

```
mosser@azrael 2aa4-mcunit % git add src/main/java
mosser@azrael 2aa4-mcunit % git commit -m "supporting test suites"
[main 8cf5b5c] supporting test suites
 6 files changed, 80 insertions(+), 62 deletions(-)
 delete mode 100644 src/main/java/tests/AddTwoIntegers.java
 create mode 100644 src/main/java/tests/IntegerTests.java
 delete mode 100644 src/main/java/tests/SubtractTwoIntegers.java
 delete mode 100644 src/main/java/tests/ThrowAnException.java
```


## 3.4: Release and Publish

```
mosser@azrael 2aa4-mcunit % git tag step_4
mosser@azrael 2aa4-mcunit % git push --tags
mosser@azrael 2aa4-mcunit % git push
```

