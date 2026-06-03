import java.util.Scanner;
class Factorial1{

public static void main(String []noman){
	
	Scanner s = new Scanner(System.in);
	System.out.print("Enter The number : " );
	int no = s.nextInt();
	
	
	
	int fact = 1;
	
	for /*(int i=1;i<=no;i++)*/ (int i=no;i>=1;i--){
		
		fact =fact*i;
		
	}
	System.out.println("Factorial of "+no+" is "+fact);
}

}