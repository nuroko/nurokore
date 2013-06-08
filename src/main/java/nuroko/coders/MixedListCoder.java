package nuroko.coders;

import java.util.ArrayList;
import java.util.List;

import nuroko.core.ICoder;

import mikera.vectorz.AVector;

/**
 * Coder for a fixed-length list of a specific item type
 * 
 * @author Mike
 * @param <T>
 */
public class MixedListCoder extends AbstractCoder<List<?>> {
	private final int listLength;
	private final ICoder<?>[] itemCoders;
	private final int codeLength;

	public MixedListCoder(ICoder<?>... itemCoders) {
		this.listLength=itemCoders.length;
		this.itemCoders=itemCoders.clone();
		
		int cl=0;
		for (ICoder<?> c: itemCoders) {		
			cl+=c.codeLength();
		}
		codeLength=cl;
	}
	
	@Override
	public List<?> decode(AVector v, int offset) {
		ArrayList<Object> al=new ArrayList<Object>();
		for (int i=0; i<listLength; i++) {
			ICoder<?> c=itemCoders[i];
			al.add(c.decode(v, offset));
			offset+=c.codeLength();
		}
		return al;
	}

	@Override
	public void encode(List<?> list, AVector dest, int offset) {
		for (int i=0; i<listLength; i++) {
			@SuppressWarnings("unchecked")
			ICoder<Object> c=(ICoder<Object>) itemCoders[i];
			Object item=list.get(i);
			c.encode(item, dest, offset);
			offset+=c.codeLength();
		}
	}

	@Override
	public int codeLength() {
		return codeLength;
	}

}
