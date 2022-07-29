package src.BlockChainPackage;

import src.VoterPackage.Vote;
import java.sql.Timestamp;

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
        return "\nTransaction:" + vote + "\nTimestamp=" + timestamp;
    }

}
