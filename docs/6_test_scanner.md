# Step #6: Test Scanner

## 6.1: Fixing a responsibility issue

1. To date, test suites are in charge of instantiating themselves. This is a burder than should not be visible to the end user. We have an encapsulation / abstraction leak here.

2. Create a `TestFactory` class, in charge of building the tests for us.
  - It'll use some introspection to look for test methods. But this will stay hidden inside the factory.

```java
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
```

3. Refactor the `IntegerTests` class to get rid of test initialization, as well as the inheritance relationship.

```java
public class IntegerTests {

    int x, y;

    public void setUp() {
        this.x = 1;
        this.y = 1;
    }

    public void test_addTwoIntegers() {
        assertEquals(x + y, 2); // should pass
    }

    public void test_subtractTwoIntegers() {
        assertEquals(x - y, 1); // Should fail (assertion error)
    }

    public void test_throwAnException() {
        throw new RuntimeException("Unexpected Error!");
    }

}
```

4. Updating the `Main` class to now rely on the factory instead of the test class directly.

```java
public class Main {
    
    public static void main(String[] args) {
        System.out.println("# 2AA4 - McUnit Demo");

        TestReport report = new TestReport();
        TestFactory scanner = new TestFactory();
        scanner.build(IntegerTests.class).run(report);
        System.out.println(report);
    }

}
```

5. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java    
# 2AA4 - McUnit Demo
# [ ] tests.IntegerTests::test_subtractTwoIntegers
# [X] tests.IntegerTests::test_addTwoIntegers
# [!] tests.IntegerTests::test_throwAnException
Summary: {[X]=1, [ ]=1, [!]=1}
mosser@azrael 2aa4-mcunit % 
```

**Well, that was quick**! Thansk to encapsluation and modularity, our system can be deeply modified, quite quickly, and with very scope-restricted modifications.

## 6.2: Record, release and publish

```
mosser@azrael 2aa4-mcunit % git add src/main/java 
mosser@azrael 2aa4-mcunit % git commit -m "supporting test instantiation as black box factory"
mosser@azrael 2aa4-mcunit % git tag step_6
mosser@azrael 2aa4-mcunit % git push --tags
mosser@azrael 2aa4-mcunit % git push
mosser@azrael 2aa4-mcunit % 
```


