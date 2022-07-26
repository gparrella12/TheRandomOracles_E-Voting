package src;

import java.util.List;

public class BlockChain {

    private List<Block> chain;

    public BlockChain() {
    }
    // ho aggiunto questo costruttore per quando la blockchain la dobbiamo richiamare
    // da file di testo, dopo la prima volta
    public BlockChain(String filename) {
    }

    public Block addBlock(Block b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
