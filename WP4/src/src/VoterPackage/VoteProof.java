package src.VoterPackage;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Random;

/**
 *
 * @author fsonnessa
 */
public class VoteProof implements Serializable{

    private String proof;

    public VoteProof(Vote v) {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        this.proof  = new String(array, Charset.forName("UTF-8"));
    }

    @Override
    public String toString() {
        return "VoteProof{" + "proof=" + proof + '}';
    }

}
