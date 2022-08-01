package VoterPackage;

import CryptographicTools.CryptographicHash;
import Utils.Utils;
import java.io.Serializable;

/**
 * This class represents the proof that a vote is valid. That is, the sent vote
 * is g<sup>0</sup> or g<sup>1</sup>
 *
 * @author fsonnessa
 */
public class VoteProof implements Serializable {

    private String proof;

    /**
     * This class creates a proof for a valid vote.
     *
     * The claim of the proof is the following: &exist; r : u = g<sup>r</sup>,
     * z= g<sup>v<sub>&Omega; <sub>j</sub></sub></sup>
     * (PK<sub>voting</sub>)<sup>r</sup>
     * &and; v<sub>&Omega;<sub>j</sub></sub> &#8714; {0,1}
     *
     * Here the implementation of the proof is simulated, and it simply consists
     * of the hash of the vote
     *
     * @param v the vote
     */
    public VoteProof(Vote v) {

        this.proof = Utils.toHex(CryptographicHash.hash(v.toString().getBytes()));

    }

    /**
     * This method returns a string representation of the vote's proof.
     *
     * @return a <code>String</code> containing the vote's proof
     */
    @Override
    public String toString() {
        return "\nProof= " + proof;
    }

}
