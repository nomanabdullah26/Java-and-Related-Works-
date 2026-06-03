public class leapYear2 {

    public static void main(String[] args) {

        int year = 1900;

        if (year%400==0 || (year%4==0 && year%100!=0) ){
			
			System.out.println("this is a leapyer !!!!");
		} else {
			
			System.out.println("this is not  a leapyer !!!!");
		}
    }
}