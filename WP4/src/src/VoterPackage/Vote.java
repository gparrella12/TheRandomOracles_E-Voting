package src.VoterPackage;

import java.io.Serializable;
import src.CryptographicTools.ElGamalHomomorphic.ElGamalCipherText;

/**
 * This class contains the encrypted vote, which will then be sent to the system
 *
 * @author franc
 */
public class Vote implements Serializable {

    private ElGamalCipherText encVote;

    /**
     * Creates a vote by passing:
     * <ul>
     * <li><code>encVote, the encrypted vote</code></li>
     * </ul>
     *
     * @param encVote the encrypted vote
     */
    public Vote(ElGamalCipherText encVote) {
        this.encVote = encVote;
    }

    /**
     * This method returns the encrypted vote of a voter
     *
     * @return the encrypted vote
     */
    public ElGamalCipherText getEncVote() {
        return encVote;
    }

    /**
     * This method returns a string representation of the vote
     *
     * @return a string containing a representation of the vote
     */
    @Override
    public String toString() {
        return "(encVote) " + encVote;
    }

}
