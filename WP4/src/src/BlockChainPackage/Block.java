package src.BlockChainPackage;

/**
 *
 * @author Speranza
 */

public class Block {
    
    // attualmente il blocco è fatto da una sola transazione    
    private Transaction transaction; 

    public Block(Transaction t) {
        this.transaction = t;
    }
    
    
    /*
    Se decidessimo in futuro di avere blocchi con più transazioni
    
    List<Transaction> listTransactions;
    
    // costruttore
    public Block() {
        listTransactions = new ArrayList<>();
    }
    
    void addTransaction(Transaction t) {
        listTransactions.add(t);
    }
    */

    @Override
    public String toString() {
        return "=== Block ===" + transaction + "=============\n";
    }
    
}
