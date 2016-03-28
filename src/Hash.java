
public class Hash {
	public static int diff ( byte [] a, byte [] b ){
		int totalDiff = 0;
		int aSize = a.length;
		int bSize = b.length;
		if( aSize > bSize ){
			totalDiff += (aSize - bSize); 
			for(int x=0;x<= bSize;x++){
				if(a[x]!=b[x]){
					totalDiff++;
				}
			}
		}else if (aSize < bSize){
			totalDiff += (bSize - aSize);
			for(int x=0;x<= aSize;x++){
				if(a[x]!=b[x]){
					totalDiff++;
				}
			}
		}else {
			
		}
		return totalDiff;
	}
	
	public static void printByteArrayInBinary(byte [] array)
	{
	 	System.out.print("[");
	   	for(int i = 0; i < array.length-1; i++)
	   {
	   		for(int j = 7; j >= 0; j--)
	       	System.out.print(
					Integer.toBinaryString((array[i]>>j)&0x01));
	        System.out.print(", ");
	    }
	    for(int j = 7; j >= 0; j--)
	    	System.out.print(
				Integer.toBinaryString((array[array.length-1]>>j)&0x01));
	    System.out.println("]");
	}

	
	public static byte [] xor(byte [] b1, byte [] b2)
	{
		byte [] r = new byte[b1.length];
	        
	   for(int i=0; i<b1.length && i<b2.length; i++)
	   	r[i] = (byte) (b1[i]^b2[i]);
	 	return r;
	}
	
	public static int countOnes(byte [] b)
	{
	    int count = 0;
	    for(int i = 0; i < b.length; i++)
	    {
	    	for(int j = 0; j < 8; j++)
	    	{
	    	count += (b[i]>>j) & 0x01;
	    	}
	    }
	    return count;
	}

}
