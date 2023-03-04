import static mcunit.Assertions.assertTrue;

public class Main {
    
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
            System.out.println("## Case 2: x + y != 4 (should FAIL)");
            assertTrue(x + y == 4);
            System.out.println("PASSED");
        } catch(AssertionError ae) {
            System.out.println("FAILED");
        }

    }

}
