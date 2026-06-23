class SelectionSort{
	
	public static void main(String []args){
		
		int[] a = {22,34,33,12,17,11,12};
		
		int min ,temp;
		for (int i= 0;i<a.length ; i++){
		    min=i;
		    for (int j=i+1;j<a.length; j++){
			    if (a[j]<a[min]){
				
				min=j;
				
			    } 
			
		    }
			temp=a[i];
			a[i]=a[min];
			a[min]=temp;
			
	    }
		System.out.print("Sorted Array: ");
        for(int i = 0; i < a.length; i++)
        {
            System.out.print(a[i] + " ");
        }
	}
	
}