import mcunit.Test;
import mcunit.TestReport;
import tests.IntegerTests;


public class Main {
    
    public static void main(String[] args) {
        System.out.println("# 2AA4 - McUnit Demo");

        TestReport report = new TestReport();
        Test integerTests = new IntegerTests();
        integerTests.run(report);
        System.out.println(report);
    }

}
