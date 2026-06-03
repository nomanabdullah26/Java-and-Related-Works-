import java.util.Scanner;

class Calculator {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String again;

        do {
            System.out.println("Enter your First Number:");
            int no1 = s.nextInt();

            System.out.println("Enter your Second Number:");
            int no2 = s.nextInt();

            System.out.println("Select Symbol (+,-,*,/):");
            String sym = s.next();

            int result = 0;

            switch (sym) {
                case "+":
                    result = no1 + no2;
                    System.out.println("Addition: " + result);
                    break;
                case "-":
                    result = no1 - no2;
                    System.out.println("Subtraction: " + result);
                    break;
                case "*":
                    result = no1 * no2;
                    System.out.println("Multiplication: " + result);
                    break;
                case "/":
                    if (no2 != 0) {
                        result = no1 / no2;
                        System.out.println("Division: " + result);
                    } else {
                        System.out.println("Error: Division by zero!");
                    }
                    break;
                default:
                    System.out.println("Invalid symbol!");
            }

            System.out.println("Do you want to calculate again? (yes/no):");
            again = s.next();
        } while (again.equalsIgnoreCase("yes"));

        s.close();
    }
}
