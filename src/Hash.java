
public class Hash {
	public static int diff ( byte [] a, byte [] b ){
		int size = Math.min(a.length,  b.length);
		int diff = 8 * Math.abs(a.length - b.length);
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < 8; j++){
				int left = (a[i] >> j) & 0x01;
				int right = (b[i] >> j) & 0x01;
				if( left != right ){
					diff++;
				}
			}
		}
		
		return diff;
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
	
	public static void main(String[] args){
		byte[] a = new byte[]{ (byte) 0xAA, (byte) 0x23, (byte) 0xFC };
		byte[] b = new byte[]{ (byte) 0x11, (byte) 0x72, (byte) 0x8B };
		
		System.out.print("a = ");
		printByteArrayInBinary(a);
		
		System.out.print("b = ");
		printByteArrayInBinary(b);
		
		int diff = diff(a, b);
		
		System.out.printf("Diff = %d",diff);
		
		
	}

}
