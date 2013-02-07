package nuroko.coders;

import nuroko.core.NurokoException;
import mikera.vectorz.AVector;

/**
 * Coder for fixed length strings. Considers short strings to be zero-padded
 * 
 * @author Mike
 *
 */
public class FixedStringCoder extends AbstractCoder<String> {
	private final int stringLength;

	private static final CharCoder charCoder=new CharCoder();
	private static final int itemLength=charCoder.codeLength();
	
	public FixedStringCoder(int length) {
		this.stringLength=length;
	}
	
	@Override
	public String decode(AVector v, int offset) {
		StringBuilder sb=new StringBuilder();
		for (int i=0; i<stringLength; i++) {
			char c=charCoder.decodeChar(v, offset+i*itemLength);
			if (c==0) break;
			sb.append(c);
		}
		return sb.toString();
	}

	@Override
	public void encode(String s, AVector dest, int offset) {
		if (s.length()>stringLength) throw new NurokoException("String length ["+s.length()+"] exceeds fixed encoder size");
		int len=Math.min(s.length(), stringLength);
		for (int i=0; i<len; i++) {
			char c=s.charAt(i);
			charCoder.encode(c, dest, offset+i*itemLength);
		}
		dest.fillRange(offset+len*itemLength, (stringLength-len)*itemLength,0.0);
	}

	@Override
	public int codeLength() {
		return stringLength*itemLength;
	}

}
