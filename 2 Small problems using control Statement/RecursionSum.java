import java.util.Scanner;
import java.util.Scanner;
public class RecursionSum {
    public static void main(String[] args) {
		Scanner s= new Scanner(System.in);
		System.out.print("enter the value of N :");
		int n = s.nextInt();
		int result = Sum(n);
		System.out.print("the number will add untill : "+n+"and the result is : "+result);

	}
	
	static int Sum(int n){
		if (n==1){
			
			return 1;
			
		}
		return n+Sum(n-1);
	  
		
	}
}