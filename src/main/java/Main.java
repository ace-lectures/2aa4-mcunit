import tests.AddTwoIntegers;
import tests.SubtractTwoIntegers;
import tests.ThrowAnException;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("# 2AA4 - McUnit Demo");

        (new AddTwoIntegers()).run();
        (new SubtractTwoIntegers()).run();
        (new ThrowAnException()).run();

    }

}
