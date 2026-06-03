
class Factorial2 {
static int fact=1;
public static void main(String []args){

   int no=5;
   Factorial2 ob= new Factorial2();
   ob.CalFact(no);
   System.out.println("the factorial of :"+no+" is "+fact);

}

    void CalFact(int no){
	if (no>=1){
		fact=fact*no;
		CalFact(no-1);
	}
	
}
}