import java.util.Scanner;

class InputFromUser{
	
	public static void main (String []args ){
		
		Scanner s = new Scanner(System.in);
		System.out.println("enter the ID :");
		int id = s.nextInt();
		
		System.out.println("enter the Name :");
		String Name = s.next();
		
		System.out.println("Enter your age :");
		int age  = s.nextInt();
		
		System.out.println("Enter your Phone Number :");
		long Phn  = s.nextLong();
		
		System.out.println("=========================================================");
		System.out.println("=========================================================");

		
		System.out.println("Id:"+id  );
		System.out.println("Name :"+Name);
		System.out.println("Age :"+age);
		System.out.println("phone Number :"+Phn);
		
		
	}
	
}