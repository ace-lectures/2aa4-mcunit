# Step #2: Introducing Test cases

## 2.1: Create the TestCase class

1. Create a class named `TestCase` in the `mcunit` package

```java
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
```

## 2.2: Refactor tests as dedicated classes

1. Create a package named `tests`
2. Create a class named `AddTwoIntegers` in this package
    - this test check that `1+1` is equals to `2`, and should succeeed

```java
public class AddTwoIntegers extends TestCase {

    @Override
    protected void test() {
        int y = 1;
        int x = 1;
        assertEquals(x + y, 2);  // should pass
    }

}
```

3. Create a class named `SubtractTwoIntegers` in the same package
    - this test check that `1-1` is equals to `2`, and should fail

```java
public class SubtractTwoIntegers extends TestCase {

    @Override
    protected void test() {
        int y = 1;
        int x = 1;
        assertEquals(x - y, 1); // Should fail (assertion error)
    }
}
```

4. Refactor `Main` to use test objects instead of the boilerplate code

```java
public class Main {
    
    public static void main(String[] args) {
        System.out.println("# 2AA4 - McUnit Demo");
        (new AddTwoIntegers()).run();
        (new SubtractTwoIntegers()).run();
    }

}
```
5. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java    
# 2AA4 - McUnit Demo
## Case tests.AddTwoIntegers
PASSED
## Case tests.SubtractTwoIntegers
FAILED
mosser@azrael 2aa4-mcunit % 
```

6. Record to VCS

```
mosser@azrael 2aa4-mcunit % git add src/main/java/Main.java src/main/java/mcunit/TestCase.java src/main/java/tests 
mosser@azrael 2aa4-mcunit % git commit -m "supporting tests as objects (template method)"
```

## 2.3: Distinguish Success, Failures and Errors

1. Update the `TestCase` class to catch exceptions that are not `AssertionError`

```java
public final void run() { 
    System.out.println("## Case " + this.getClass().getCanonicalName());
    try {
        test(); // <<-- Design Pattern: Template method
        System.out.println("PASSED");
    } catch(AssertionError ae) {
        System.out.println("FAILED");
    } catch (Exception e) {
        System.out.println("ERROR");
    }
}
```

2. Create a test class named `ThrowAnException` to throw an exception

```java 
public class ThrowAnException extends TestCase {

    @Override
    protected void test() {
        throw new RuntimeException("Unexpected Error!");
    }

}
```

3. Update `Main` to run this new test

```java
public static void main(String[] args) {
    System.out.println("# 2AA4 - McUnit Demo");

    (new AddTwoIntegers()).run();
    (new SubtractTwoIntegers()).run();
    (new ThrowAnException()).run();

}
```

4. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package  
mosser@azrael 2aa4-mcunit % mvn -q exec:java
# 2AA4 - McUnit Demo
## Case tests.AddTwoIntegers
PASSED
## Case tests.SubtractTwoIntegers
FAILED
## Case tests.ThrowAnException
ERROR
mosser@azrael 2aa4-mcunit %
```

5. Record to VCS

```
mosser@azrael 2aa4-mcunit % git add src/main/java/Main.java  src/main/java/mcunit/TestCase.java src/main/java/tests/ThrowAnException.java 
mosser@azrael 2aa4-mcunit % git commit -m "supporting success, failures and errors"
```

## 2.4: Release and publish

```
mosser@azrael 2aa4-mcunit % git tag step_2
mosser@azrael 2aa4-mcunit % git push --tags
mosser@azrael 2aa4-mcunit % git push
```
