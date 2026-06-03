import java.util.Scanner;
class Cal {
    public static void main(String[] args) {
      String yn;
	  do{
		   Scanner s= new Scanner(System.in);
	   System.out.println("Enter the First Number: ");
	   int no1=s.nextInt();
	   System.out.println("Enter the Second Number: ");
	   int no2=s.nextInt();
	   
	   System.out.println("Select Symble (+,-,*,/)");
	   String sym =s.next();
		
		int res;
		
		switch(sym){
			
			case"+":res=no1+no2;
			System.out.println("addtion is :" +res);
			break;
			
			case"-":res=no1-no2;
			System.out.println("Subtraction is :" +res);
			break;
			
			case"*":res=no1*no2;
			System.out.println("Multplication is :" +res);
			break;
			
			case"/":res=no1/no2;
			System.out.println("Division is :" +res);
			break;
			
			default :System.out.println("Invlid Symble");
		}
		System.out.println("Do you want to continue(press y for yes n for No)");
		yn=s.next();
	  }
	  while(yn.equals("y")||yn.equals("Y"));
		
	   
    }
}
