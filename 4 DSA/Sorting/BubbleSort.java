class BubbleSort{
	
	public static void main(String[] args){
		
		int[] a= {22,34,33,12,17,11,12};
		int temp ;
		
		for (int i = 0; i<a.length-1 ; i++ )
			
		{
			int flag=0;
			for (int j=0;j<a.length-1;j++)
			{   
		         
				if (a[j]>a[j+1])
				{
					temp =a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
					flag =1;
				}
			
			}
			if (flag==0){
				break;
			}
		}
		for (int i = 0; i<a.length ; i++ ){
			System.out.print(a[i]+" ");
		}
		
	} 
}