package src.VoterPackage;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import static src.SchnorrNIZKP.SchnorrNIZKP.getHashFunction;
import src.Utils;

/**
 * This class contains the digital signature for the integrity and non-repudiation of the vote
 *
 * @author fsonnessa
 */
public class VoteProof implements Serializable {

    private String proof;

    /**
     * Once a Vote <code>v</code> is entered, its digital signature is created.
     * The signature is done through a hash function.
     * <code>Sign = H(Vote)</code>
     * 
     * @param v
     */
    public VoteProof(Vote v) {
        MessageDigest h = null;
        try {
            h = MessageDigest.getInstance(getHashFunction(), new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        byte digest[] = new byte[h.getDigestLength()];

        digest = h.digest(v.toString().split(" ")[1].getBytes());
        this.proof = Utils.toHex(digest);

        // v.toString() = "(encVote) " + encVote;
//        System.out.println(v.toString().split(" ")[1]);
//        System.out.println(Utils.toHex(v.toString().split(" ")[1].getBytes()));
//        System.out.println("H: " + Utils.toHex(digest) + '\n');
    }

    /**
     * Returns the proof, that is the digital signature of the vote
     * @return
     */
    @Override
    public String toString() {
        return "\nProof= " + proof;
    }

}
