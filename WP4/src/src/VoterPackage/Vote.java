package src.VoterPackage;

import java.io.Serializable;
import src.CryptographicTools.ElGamalHomomorphic.ElGamalCipherText;

/**
 * This class contains the encrypted vote to be passed to the system for the
 * next count
 *
 * @author franc
 */
public class Vote implements Serializable {

    private ElGamalCipherText encVote;

    /**
     * Create a Vote by passing the ElGamalCipherText of the Voter's preference
     *
     * @param encVote
     */
    public Vote(ElGamalCipherText encVote) {
        this.encVote = encVote;
    }

    /**
     * Return the ElGamalCipherText of the preference
     *
     * @return
     */
    public ElGamalCipherText getEncVote() {
        return encVote;
    }

    /**
     * Return the String rappresentation of a Vote
     *
     * @return
     */
    @Override
    public String toString() {
        return "(encVote) " + encVote;
    }

}
