# Step #0: Technical environment

The project will be supported by Maven.

## 0.1: Creating the required files

```
mosser@azrael 2aa4-mcunit % mkdir -p src/main/java 
mosser@azrael 2aa4-mcunit % touch .gitignore
mosser@azrael 2aa4-mcunit % touch pom.xml
```

## 0.2: Excluding files from VCS

We want to ignore temporary files, JAR files, maven files, and IDE-specific (here IntelliJ) files to be added into the version control system.

```
*~
*.jar
target/
.idea/
*.iml
```

## 0.3: Creating the Project Object Model

We create a very basic POM in the `pom.xml` file

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
  http://maven.apache.org/maven-v4_0_0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <packaging>jar</packaging>

  <groupId>io.github.ace-lectures.2aa4</groupId>
  <artifactId>mcunit</artifactId>
  <version>1.0-SNAPSHOT</version>
  <name>ACE :: 2AA4 :: McUnit</name>

  <properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <build>
    <plugins>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>1.6.0</version>
        <configuration>
          <mainClass>Main</mainClass>
        </configuration>
      </plugin>
    </plugins>
  </build>

</project>
```

## 0.4: Creating a dummy class to check compilation

We create a `Main.java` class in the `src/main/java` directory.

```java
public class Main {
    
    public static void main(String[] args) {
        System.out.println("# 2AA4 - McUnit Demo");
    }

}
```

## 0.5: Checking compilation and execution

```
mosser@azrael 2aa4-mcunit % mvn clean package
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------< io.github.ace-lectures.2aa4:mcunit >-----------------
[INFO] Building ACE :: 2AA4 :: McUnit 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
...
[INFO] --- jar:3.3.0:jar (default-jar) @ mcunit ---
[INFO] Building jar: /Users/mosser/work/teaching/2AA4/demos/2aa4-mcunit/target/mcunit-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.627 s
[INFO] Finished at: 2023-03-04T15:42:31-05:00
[INFO] ------------------------------------------------------------------------
mosser@azrael 2aa4-mcunit % mvn -q exec:java 
# 2AA4 - McUnit Demo
mosser@azrael 2aa4-mcunit % 
```

## 0.6: Pushing and tagging

```
mosser@azrael 2aa4-mcunit % git add .gitignore pom.xml src 
mosser@azrael 2aa4-mcunit % git commit -m "Initial technical stack"
[main f28ce58] Initial technical stack
 3 files changed, 46 insertions(+)
 create mode 100644 .gitignore
 create mode 100644 pom.xml
 create mode 100644 src/main/java/Main.java
mosser@azrael 2aa4-mcunit % git tag step_0 
mosser@azrael 2aa4-mcunit % git push --tags
Enumerating objects: 9, done.
Counting objects: 100% (9/9), done.
Delta compression using up to 8 threads
Compressing objects: 100% (4/4), done.
Writing objects: 100% (8/8), 1.09 KiB | 1.09 MiB/s, done.
Total 8 (delta 0), reused 0 (delta 0), pack-reused 0
To github.com:ace-lectures/2aa4-mcunit.git
 * [new tag]         step_0 -> step_0
```