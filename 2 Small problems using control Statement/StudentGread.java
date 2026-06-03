import java.util.Scanner;
class StudentGread{
	public static void main(String []arg){
		
		Scanner s=new Scanner(System.in);
		
		System.out.println("enter your mark: ");
		int mark = s.nextInt();
		GreadCalculation(mark);
	}
	static void GreadCalculation(int mark){
		
		if (mark>=80)
		{
			System.out.println("you got A+");
		}
		else if (mark>=70){
			System.out.println("you got A");
		}
		else if (mark>=60){
			System.out.println("you got A-");
		}
		else if (mark>=50){
			System.out.println("you got B");
		}
		else {
			System.out.println("you got F");
		}
		
	}


}