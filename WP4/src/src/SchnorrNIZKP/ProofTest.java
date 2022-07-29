package src.SchnorrNIZKP;

import java.security.NoSuchAlgorithmException;
import src.ElGamalHomomorphic.CyclicGroupParameters;
import src.ElGamalHomomorphic.ElGamalKeyPair;

/**
 *
 * @author gparrella
 */
public class ProofTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // TODO code application logic here
        ElGamalKeyPair p = new ElGamalKeyPair();
        CyclicGroupParameters param = new CyclicGroupParameters();
        SchnorrNIProof proof = SchnorrNIZKP.makeProof(p.getSecretKey(), p.getPublicKey(), param);
        System.out.println(SchnorrNIZKP.verityProof(proof, p.getPublicKey(), param));
    }

}
