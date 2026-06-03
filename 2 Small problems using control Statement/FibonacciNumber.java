import java.util.Scanner;

class FibonacciNumber{
    public static void main(String[] args) {
        Scanner s = new Scanner (System.in);
      	System.out.print("how  many terms You wnat to print :");		
		int n= s.nextInt();
		System.out.print("the searise will bo on " +n+ "numbers");
		
		LogicOfFibonacci(n);
      
    
        }
		static void LogicOfFibonacci(int n ){
			
			int first=0,second =1;
			
			for (int i=1;i<n+1;i++){
				System.out.print(first+" ");
				int next= first+second;
				first=second ;
				second=next;
			
			}
		}
    
}