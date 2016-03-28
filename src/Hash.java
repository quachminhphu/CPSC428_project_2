
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
	
	
}
