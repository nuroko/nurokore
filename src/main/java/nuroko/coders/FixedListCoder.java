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
public class FixedListCoder<T> extends AbstractCoder<List<T>> {
	private final int listLength;
	private final ICoder<T> itemCoder;
	private final int itemLength;

	public FixedListCoder(int listLength, ICoder<T> itemCoder) {
		this.listLength=listLength;
		this.itemCoder=itemCoder;
		itemLength=itemCoder.codeLength();
	}
	
	@Override
	public List<T> decode(AVector v, int offset) {
		ArrayList<T> al=new ArrayList<T>();
		for (int i=0; i<listLength; i++) {
			al.add(itemCoder.decode(v, offset+i*itemLength));
		}
		return al;
	}

	@Override
	public void encode(List<T> list, AVector dest, int offset) {
		for (int i=0; i<listLength; i++) {
			T item=list.get(i);
			itemCoder.encode(item, dest, offset+i*itemLength);
		}
	}

	@Override
	public int codeLength() {
		return listLength*itemLength;
	}

}
