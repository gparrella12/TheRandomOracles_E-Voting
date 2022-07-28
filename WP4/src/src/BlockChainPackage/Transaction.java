package src.BlockChainPackage;

import java.sql.Timestamp;
import src.Voter.*;

/**
 *
 * @author Speranza
 */

public class Transaction {

    private Vote vote;
    private Timestamp timestamp;

    public Transaction(Vote v, Timestamp timestamp) {
        this.vote = v;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction: " + "vote=" + vote + ", timestamp=" + timestamp;
    }
    
}
