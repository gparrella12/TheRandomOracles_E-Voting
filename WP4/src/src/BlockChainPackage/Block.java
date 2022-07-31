package src.BlockChainPackage;

import java.io.Serializable;

/**
 * This class represents a Block of the BlockChain.
 *
 * @author Speranza
 * @param <T> type of data that will be cointained in the Block
 */
public class Block<T> implements Serializable {

    private T data;

    /**
     * Constructor of <code>Block</code> class.
     *
     * @param data data that will be contained in the Block.
     */
    public Block(T data) {
        this.data = data;
    }

    /**
     * Function used to print the data on the BlockChain (in our case, on a file
     * .txt)
     *
     * @return
     */
    @Override
    public String toString() {
        return "\n======== Block ========\n" + data + "\n=======================\n";
    }

}
