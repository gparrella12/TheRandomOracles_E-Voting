package src.VoterPackage;

import java.io.Serializable;
import src.ElGamalHomomorphic.ElGamalCipherText;

/**
 *
 * @author franc
 */
public class Vote implements Serializable{

    private ElGamalCipherText encVote;

    /**
     *
     * @param encVote
     */
    public Vote(ElGamalCipherText encVote) {
        this.encVote = encVote;
    }

    /**
     *
     * @return
     */
    public ElGamalCipherText getEncVote() {
        return encVote;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Vote{" + "encVote=" + encVote + '}';
    }

}
