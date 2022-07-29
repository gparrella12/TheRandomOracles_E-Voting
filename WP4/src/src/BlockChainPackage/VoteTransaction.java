package src.BlockChainPackage;

import java.io.Serializable;
import src.VoterPackage.Vote;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import src.Utils;
import src.VoterPackage.VoteProof;

/**
 *
 * @author Speranza
 */
public class VoteTransaction implements Serializable {

    private Vote vote;
    private VoteProof vp;
    private byte[] sign;
    private Timestamp timestamp;

    public VoteTransaction(Vote vote, VoteProof vp, byte[] sign) {
        this.vote = vote;
        this.vp = vp;
        this.sign = sign;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "VoteTransaction{" + "vote=" + vote + ", vp=" + vp + ", sign=" + Utils.toHex(sign) + ", timestamp=" + timestamp + '}';
    }

}
