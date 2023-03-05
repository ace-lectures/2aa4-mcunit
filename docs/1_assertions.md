# Step #1: Introducing Assertions

## 1.1: Create the Assertions class & assertTrue

1. Create a `mcunit` package
2. Create a class `Assertions`
3. Define a static method `assertTrue` throwing an `AssertionError`

```java
package mcunit;

public class Assertions {

    public static void assertTrue(boolean condition) {
        if (!condition)
            throw new AssertionError();
    }

}
```

4. Update `Main` to check assertions usage

```java
public static void main(String[] args) {
    System.out.println("# 2AA4 - McUnit Demo");

    int x = 1;
    int y = 1;

    try {
        System.out.println("## Case 1: x + y == 2 (should PASS)");
        assertTrue(x + y == 2);
        System.out.println("PASSED");
    } catch(AssertionError ae) {
        System.out.println("FAILED");
    }

    try {
        System.out.println("## Case 2: x + y == 4 (should FAIL)");
        assertTrue(x + y == 4);
        System.out.println("PASSED");
    } catch(AssertionError ae) {
        System.out.println("FAILED");
    }

}
```

5. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java
# 2AA4 - McUnit Demo
## Case 1: x + y == 2 (should PASS)
PASSED
## Case 2: x + y == 4 (should FAIL)
FAILED
```

6. Record into VCS

```
mosser@azrael 2aa4-mcunit % git add src/main/java/Main.java src/main/java/mcunit/Assertions.java 
mosser@azrael 2aa4-mcunit % git commit -m "supporting assertTrue"
```



## 1.2: Introduce assertFalse

1. Define an `assertFalse` method in `Assertions`

``` java
public static void assertFalse(boolean condition) {
    assertTrue(!condition);
}
```

2. Update `Main` to make the second test case pass by using `assertFalse`

```java
try {
    System.out.println("## Case 2: x + y != 4 (should PASS)");
    assertFalse(x + y == 4);
    System.out.println("PASSED");
} catch(AssertionError ae) {
    System.out.println("FAILED");
}
```

3. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package
mosser@azrael 2aa4-mcunit % mvn -q exec:java    
# 2AA4 - McUnit Demo
## Case 1: x + y == 2 (should PASS)
PASSED
## Case 2: x + y != 4 (should PASS)
PASSED
```

4. Record into VCS

```
mosser@azrael 2aa4-mcunit % git add src/main/java/Main.java src/main/java/mcunit/Assertions.java 
mosser@azrael 2aa4-mcunit % git commit -m "supporting assertFalse"
```


## 1.3 Introduce assertEquals & assertNotEquals

1. Define an `assertEquals` method in `Assertions`. 
    - It leverages the public contract defined by `Object::equals` (reflexive, transitive, symmetric)
    - Technical debt: `left` can't be `null`.

```java
public static void assertEquals(Object left, Object right) {
    assertTrue(left.equals(right));
}

public static void assertNotEquals(Object left, Object right) {
    assertFalse(left.equals(right));
}
```
 2. Update `Main` to get rid of the low-level assertions and used business-driven ones

 ```java
try {
    System.out.println("## Case 1: x + y == 2 (should PASS)");
    assertEquals(x + y, 2);
    System.out.println("PASSED");
} catch(AssertionError ae) {
    System.out.println("FAILED");
}

try {
    System.out.println("## Case 2: x + y != 4 (should PASS)");
    assertNotEquals(x + y, 4);
    System.out.println("PASSED");
} catch(AssertionError ae) {
    System.out.println("FAILED");
}
 ```

3. Compile and execute

```
mosser@azrael 2aa4-mcunit % mvn -q clean package                                                 
mosser@azrael 2aa4-mcunit % mvn -q exec:java                                                     
# 2AA4 - McUnit Demo
## Case 1: x + y == 2 (should PASS)
PASSED
## Case 2: x + y != 4 (should PASS)
PASSED
```

4. Record into the VCS

```
mosser@azrael 2aa4-mcunit % git add src/main/java/Main.java src/main/java/mcunit/Assertions.java 
mosser@azrael 2aa4-mcunit % git commit -m "supporting assertEquals and assertNotEquals"
```

## 1.4 Release and publish

```
mosser@azrael 2aa4-mcunit % git tag step_1
mosser@azrael 2aa4-mcunit % git push --tags
mosser@azrael 2aa4-mcunit % git push
```



