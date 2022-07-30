package src.VoterPackage;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import static src.SchnorrNIZKP.SchnorrNIZKP.getHashFunction;
import src.Utils;

/**
 *
 * @author fsonnessa
 */
public class VoteProof implements Serializable {

    private String proof;

    /**
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
        digest = h.digest(v.toString().getBytes());
        this.proof = Utils.toHex(digest);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "\nProof= " + proof;
    }

}
