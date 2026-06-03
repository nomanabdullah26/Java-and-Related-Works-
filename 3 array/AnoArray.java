class AnoArray{
	
	public static void main (String []args){
		
		AnoArray ob=new AnoArray();
		ob.Sum(new int[]{10,20,30});
	}
	     void Sum(int[] no){
		int total= 0;
		for (int i: no){
			total =total+i;
			
			}
			System.out.println("Sum is :"+total);
	}
}