package src.VoterPackage;

import java.io.Serializable;
import src.CryptographicTools.CryptographicHash;
import src.Utils.Utils;

/**
 * This class contains the digital signature for the integrity and
 * non-repudiation of the vote
 *
 * @author fsonnessa
 */
public class VoteProof implements Serializable {

    private String proof;

    /**
     * This class create a proof for a valid vote (that encrypt 0 or 1 with the
     * correct PK). In our case, we are simulating this proof with H(v), with H
     * random oracle.
     *
     * @param v the vote
     */
    public VoteProof(Vote v) {

        this.proof = Utils.toHex(CryptographicHash.hash(v.toString().getBytes()));

    }

    /**
     * Returns the proof, that is the digital signature of the vote
     *
     * @return
     */
    @Override
    public String toString() {
        return "\nProof= " + proof;
    }

}
