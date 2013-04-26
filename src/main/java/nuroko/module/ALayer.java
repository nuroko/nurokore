package nuroko.module;

/**
 * Layers represent layers of computation from input to output nodes
 * 
 * @author Mike
 *
 */
public abstract class ALayer extends AStateComponent {

	public ALayer(int inputLength, int outputLength) {
		super(inputLength, outputLength);
	}

	public abstract ALayer clone();
}
