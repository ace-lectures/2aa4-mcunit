import mcunit.TestReport;
import tests.AddTwoIntegers;
import tests.SubtractTwoIntegers;
import tests.ThrowAnException;

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
