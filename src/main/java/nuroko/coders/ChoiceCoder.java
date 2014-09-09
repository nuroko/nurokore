package nuroko.coders;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import mikera.vectorz.AVector;
import mikera.vectorz.BitVector;
import nuroko.core.NurokoException;

/**
 * Coder for a categorical choice variable, with a small fixed set of values.
 * 
 * @author Mike
 *
 * @param <T>
 */
public class ChoiceCoder<T> extends AbstractCoder<T> {
	final int length;
	final Object[] choiceArray;
	final HashMap<T,Integer> choiceMap=new HashMap<T,Integer>();

	public ChoiceCoder(Collection<T> choices) {
		length=choices.size();
		choiceArray=new Object[length];
		int i=0;
		for (T t: choices) {
			choiceArray[i]=t;
			choiceMap.put(t, i);
			i++;
		}
	}
	
	public ChoiceCoder(T... values) {
		this(Arrays.asList(values));
	}

	@Override 
	public int codeLength() {
		return length;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T decode(AVector v, int offset) {
		int choice=0;
		double max=Double.NEGATIVE_INFINITY;
		for (int i=0; i<length; i++) {
			double d=v.get(offset+i);
			if (d>max) {
				max=d;
				choice=i;
			}
		}
		return (T)choiceArray[choice];
	}

	@Override
	public void encode(T object, AVector dest, int offset) {
		Integer i=choiceMap.get(object);
		if (i==null) throw new NurokoException("Choice value not found: "+object);
		dest.fillRange(offset, length, 0.0);
		dest.set(offset+i,1.0);
	}

	public List<?> getChoiceList() {
		return (List<?>) Arrays.asList(choiceArray);
	}
	
	@Override
	public AVector createOutputVector() {
		return BitVector.createLength(codeLength());
	}
}
