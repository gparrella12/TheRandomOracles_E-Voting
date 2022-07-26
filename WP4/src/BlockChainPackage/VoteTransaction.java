package BlockChainPackage;

import Utils.Utils;
import VoterPackage.Vote;
import VoterPackage.VoteProof;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * This Class represents a <code>Vote</code> transaction.
 *
 * @author Speranza
 */
public class VoteTransaction implements Serializable {

    private Vote vote;
    private VoteProof vp;
    private byte[] sign;
    private Timestamp timestamp;

    /**
     *
     * Creates the <code>VoteTransaction</code>, by passing:
     *
     * <ul>
     * <li><code> the vote of a voter</code></li>
     * <li><code> the proof of validity of the vote</code></li>
     * <li><code> the signature of (vote || proof of vote) </code></li>
     * </ul>
     * Constructor of the <code>VoteTransaction</code> Class.
     *
     * @param vote vote made by a voter
     * @param vp proof of validity of the vote
     * @param sign signature of (vote || proof of vote)
     */
    public VoteTransaction(Vote vote, VoteProof vp, byte[] sign) {
        this.vote = vote;
        this.vp = vp;
        this.sign = sign;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * This method prints information of the vote transaction.
     *
     * @return a <code>String</code> containing information of the vote transaction.
     */
    @Override
    public String toString() {
        return "VoteTransaction:\nVote= " + vote + vp + "\nSign= " + Utils.toHex(sign) + "\nTimestamp= " + timestamp;
    }

}
