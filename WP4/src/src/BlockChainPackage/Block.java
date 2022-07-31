package src.BlockChainPackage;

import java.io.Serializable;

/**
 * This class represents a Block of the BlockChain.
 *
 * @author Speranza
 * @param <T> type of data that will be contained in the Block
 */
public class Block<T> implements Serializable {

    private T data;

    /**
     * Creates a block by passing:
     * <ul>
     * <li><code>the data that will be contained in the Block</code></li>
     * </ul>
     *
     * @param data that will be contained in the Block.
     */
    public Block(T data) {
        this.data = data;
    }

    /**
     * This method prints information of the block.     
     *
     * @return a <code>String</code> containing information of the block.  
     */
    @Override
    public String toString() {
        return "\n======== Block ========\n" + data + "\n=======================\n";
    }

}
