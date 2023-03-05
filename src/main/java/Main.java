import mcunit.Test;
import mcunit.TestFactory;
import mcunit.TestReport;
import tests.IntegerTests;


public class Main {
    
    public static void main(String[] args) {
        System.out.println("# 2AA4 - McUnit Demo");

        TestReport report = new TestReport();
        TestFactory scanner = new TestFactory();
        scanner.build(IntegerTests.class).run(report);
        System.out.println(report);
    }

}
