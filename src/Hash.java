import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Hash {
	
	public static final int MESSAGE_LENGTH = 8;
	public static final int HASH_LENGTH = 4;
	public static final String HASH_TYPE = "SHA-1";
	
	public static int diff ( byte [] a, byte [] b ){
		int size = Math.min(a.length,  b.length);
		int diff = 8 * Math.abs(a.length - b.length);
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < 8; j++){
				int left  = (a[i] >> j) & 0x01;
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
	
	public static byte[] flip(byte[] array, int pos)
	{
		byte[] result = new byte[array.length];
		
		int index = -1;
		int bit   = -1;
		if(pos > -1 && pos < array.length * 8){
			index = pos / 8;
			bit   = 7 - pos % 8;
		}
		
		for(int i = 0; i < array.length; i++){
			if(i == index){
				result[i] = (byte) (array[i] ^ ( 0b01 << bit ));	
			} else {
				result[i] = array[i];
			}
		}
		
		return result;
	}
	
	public static void flipWithoutNew(byte[] array, int pos){
		if(pos > -1 && pos < array.length * 8){
			int index = pos / 8;
			int bit   = 7 - pos % 8;
			array[index] = (byte) (array[index] ^ ( 0b01 << bit ));	
		}
	}
	
	public static boolean bitEqual(byte[] a, byte[] b, int pos){
		int index = pos / 8;
		int bit   = 7 - pos % 8;
		
		int left = a[index] & (0b01 << bit);
		int right = b[index] & (0b01 << bit);
		
		return left == right;
	}
	
	public static byte[] generateMessage(int length){
		byte[] result = new byte[length];
		
		Random random = new Random();
		
		for(int i = 0; i < length; i++){
			result[i] = (byte) random.nextInt();
		}
		
		return result;
	}
	
	public static byte[] hash(byte[] input, int size){
		byte[] hash;
		
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance(HASH_TYPE);
		} catch (Exception e){
			e.printStackTrace();
		}
		hash = md.digest(input);
		
		if(size > hash.length)
			throw new IllegalArgumentException("Size is too large!");
		
		byte[] result = new byte[size];
		
		for(int i = 0; i < size; i++){
			result[i] = hash[i];
		}
		
		return result;
	}
	
	public static String byteArrayToHex(byte [] array)
	{
		String s = new String();
		for(int i = 0; i < array.length-1; i++)
		{
			s = s + (Integer.toHexString((array[i]>>4)&0x0F).toUpperCase());
			s = s + (Integer.toHexString(array[i]&0x0F).toUpperCase() );
		}

		s = s + (Integer.toHexString(array[array.length-1]>>4&0x0F).toUpperCase());
		s = s +(Integer.toHexString(array[array.length-1]&0x0F).toUpperCase());
		return s;
	}
	
	public static double average(Integer[] array){
		int sum = 0;
		
		for(int i = 0; i < array.length; i++){
			sum += array[i];
		}
		
		double average = (double) sum / array.length;
		
		return average;
	}
	
	public static void main(String[] args){
		byte[] message = generateMessage(MESSAGE_LENGTH);
		byte[] hashA   = hash(message, HASH_LENGTH);
		byte[] hashB   = hashA.clone();
		
		System.out.printf("Message: %s%n",byteArrayToHex(message));
		System.out.printf("Hashed:  %s%n",byteArrayToHex(hashA));
		
		Integer[] diff  = new Integer[ message.length * 8 ];
		int[] bitTally = new int[ HASH_LENGTH * 8 ];
	
		//Index of diffMessage is the # of bits changed (+ 1)
		
		for(int i = 0; i < diff.length; i++){
			flipWithoutNew(message, i);
			
			hashA = hash(message, HASH_LENGTH);
			
			diff[i] = diff( hashA, hashB );
			
			
			//Track the difference bteween bits in hashA and hashB
			for(int j = 0; j < bitTally.length; j++){
				if(bitEqual(hashA, hashB, j))
					bitTally[j]++;
			}
			
			hashB = hashA.clone();
		}
		
		List<Integer> diffList = Arrays.asList(diff);
		
		int max = Collections.max(diffList);
		int min = Collections.min(diffList);
		double avg = average(diff);
		System.out.printf("MAX: %d%nMIN: %d%nAVG: %.2f%n", max, min, avg);
		
		
		System.out.print("DIFF:  [");
		for(int i = 0; i < diff.length; i++){
			System.out.print(diff[i]);
			if(i < diff.length - 1)
				System.out.print(", ");
			else
				System.out.println("]");
		}
		
		System.out.print("TALLY: [");
		for(int i = 0; i < bitTally.length; i++){
			System.out.print(bitTally[i]);
			if(i < bitTally.length - 1)
				System.out.print(", ");
			else
				System.out.println("]");
		}
	}

}
