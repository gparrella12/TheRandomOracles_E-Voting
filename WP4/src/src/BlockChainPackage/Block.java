package src.BlockChainPackage;

public class Block {

    private Transaction transaction; // attualmente il blocco è fatto da una sola transazione

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
    
}
