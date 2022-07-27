package src.BlockChainPackage;

import java.util.*;

public class BlockChain {

    private List<Block> chain;

    public BlockChain() {
        chain = new ArrayList<>();
    }
    // ho aggiunto questo costruttore per quando la blockchain la dobbiamo richiamare
    // da file di testo, dopo la prima volta
    public BlockChain(String filename) {
        
    }

    void addBlock(Block b) {
        chain.add(b);
    }
    
}
