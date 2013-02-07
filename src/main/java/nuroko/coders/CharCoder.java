package nuroko.coders;

import mikera.vectorz.AVector;

public class CharCoder extends AbstractCoder<Character> {

	public CharCoder() {
		// nothing to do
	}
	
	@Override
	public Character decode(AVector v, int offset) {
		return decodeChar(v,offset);
	}
	
	public char decodeChar(AVector v, int offset) {
		int c=0;
		for (int i=0; i<8; i++) {
			c<<=1;
			double x=v.get(offset+i);
			if (x>=0.5) {
				c+=1;
			}
		}		
		return (char)c;
	}

	@Override
	public void encode(Character c, AVector dest, int offset) {
		encode(c.charValue(),dest,offset);	
	}
	
	public void encode(char c, AVector dest, int offset) {
		for (int i=0; i<8; i++) {
			dest.set(offset+i,((c&128)==0)?0.0:1.0);
			c<<=1;
		}			
	}
	


	@Override
	public int codeLength() {
		return 8;
	}

	
}
