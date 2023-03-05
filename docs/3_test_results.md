# Step #3: Collecting Tests results

## 3.1: Reifying status as first-class citizens

1. Create an enum named `STATUS` in the `mcunit` package.
    - it binds a character symbol to a test result status (for the reports)

```java
public enum STATUS {
    PASSED ('X'),
    FAILED (' '),
    ERRORED('!');

    private char symbol;
    private STATUS(char s) { this.symbol = s; }

    @Override
    public String toString() { return "[" + symbol + ']'; }
}
```

## 3.2: Reify TestResult as objects

1. Create a `TestResult` class in the `mcunit` package

```java
public class TestResult {

    private final String testName;
    private STATUS status;

    public TestResult(String name) {
        this.testName = name;
    }

    public void record(STATUS status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "# " + status + " " + testName;
    }

}
```

## 3.3: Collect Test results into a report

1. Create a `TestReport` class that will aggregate each individual report into a comprehensive report

```java
public class TestReport {

    private final List<TestResult> results = new ArrayList<>();
    private final Map<STATUS, Integer> counter = new HashMap<>();

    public void collect(TestResult result) {
        this.results.add(result);
        Integer count = counter.getOrDefault(result.status(),0) + 1;
        this.counter.put(result.status(), count);
    }

    @Override
    public String toString() {
        String details = results.stream()
                .map(TestResult::toString)
                .collect(Collectors.joining("\n"));
        return details + "\nSummary: " + counter.toString();
    }

}
```

2. By coding this class, we realize we need an access to the `status` attribute of a given result. We add the method in the `TestResult` class

```java
public STATUS status() {
    return this.status
}
```

## 3.4: Refactor TestCase to produce a TestResult

1. Update the `TestCase` class to remove the _sout_ and replace it by adequate status.

```java
public final TestResult run() {
    TestResult result = new TestResult(this.getClass().getCanonicalName());
    try {
        test(); // <<-- Design Pattern: Template method
        result.record(STATUS.PASSED);
    } catch(AssertionError ae) {
        result.record(STATUS.FAILED);
    } catch (Exception e) {
        result.record(STATUS.ERRORED);
    }
    return result;
}
```

## 3.5: Validate the changes by updating Main

1. Update the Main class to use `TestResult`s collected into a `TestReport`

```java
public class Main {
    
    public static void main(String[] args) {
        System.out.println("# 2AA4 - McUnit Demo");

        TestReport report = new TestReport();
        report.collect(new AddTwoIntegers().run());
        report.collect(new SubtractTwoIntegers().run());
        report.collect(new ThrowAnException().run());

        System.out.println(report);
    }

}
```

2. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java    
# 2AA4 - McUnit Demo
# [X] tests.AddTwoIntegers
# [ ] tests.SubtractTwoIntegers
# [!] tests.ThrowAnException
Summary: {[ ]=1, [!]=1, [X]=1}
mosser@azrael 2aa4-mcunit % 
```

3. Record into VCS

```
mosser@azrael 2aa4-mcunit % git add src/main/java
mosser@azrael 2aa4-mcunit % git commit -m "introducing TestResult and TestReport"
[main 56a8b20] introducing TestResult and TestReport
 5 files changed, 74 insertions(+), 10 deletions(-)
 create mode 100644 src/main/java/mcunit/STATUS.java
 create mode 100644 src/main/java/mcunit/TestReport.java
 create mode 100644 src/main/java/mcunit/TestResult.java
```

## 3.6: Nice to have: setUp and tearDown

We can squeeze in this release a nice way to define preprocessing and postprocessing whenexecuting tests.

1. Edit the `TestCase` class to introduce this logic

```java
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

    protected void setUp() { }    // <--default behaviour: do nothing
    protected void tearDown() { } // <--default behaviour: do nothing

    protected abstract void test();

}
```

2. Refactor the `AddTwoIntegers` class to use `setUp` to initialize the fixture

```java
public class AddTwoIntegers extends TestCase {

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
```

3. Refactor the `SubtractTwoIntegers` class to use `setUp` to initialize the fixture

```java
public class SubtractTwoIntegers extends TestCase {

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
```

4. Compile and execute.

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java    
# 2AA4 - McUnit Demo
# [X] tests.AddTwoIntegers
# [ ] tests.SubtractTwoIntegers
# [!] tests.ThrowAnException
Summary: {[ ]=1, [!]=1, [X]=1}
mosser@azrael 2aa4-mcunit % 
```

5. Record into VCS

```
mosser@azrael 2aa4-mcunit % git add src
mosser@azrael 2aa4-mcunit % git commit -m "supporting setUp and tearDown"
```

## 3.7: Release and publish

```
mosser@azrael 2aa4-mcunit % git tag step_3
mosser@azrael 2aa4-mcunit % git push --tags
mosser@azrael 2aa4-mcunit % git push
```
