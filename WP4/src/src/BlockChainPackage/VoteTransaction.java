package src.BlockChainPackage;

import java.io.Serializable;
import src.VoterPackage.Vote;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import src.Utils;
import src.VoterPackage.VoteProof;

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
     * Constructor of the <code>VoteTransaction</code> Class.
     *
     * @param vote vote made by a voter.
     * @param vp proof of correctness of the vote.
     * @param sign signature.
     */
    public VoteTransaction(Vote vote, VoteProof vp, byte[] sign) {
        this.vote = vote;
        this.vp = vp;
        this.sign = sign;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * Function used to print the informations on the BlockChain (in our case,
     * on a file .txt)
     *
     * @return
     */
    @Override
    public String toString() {
        return "VoteTransaction:\nVote= " + vote + vp + "\nSign= " + Utils.toHex(sign) + "\nTimestamp= " + timestamp;
    }

}
