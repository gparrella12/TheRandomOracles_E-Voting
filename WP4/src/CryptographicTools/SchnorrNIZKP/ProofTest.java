package CryptographicTools.SchnorrNIZKP;

import CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import CryptographicTools.ElGamalHomomorphic.ElGamalKeyPair;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 *
 * @author gparrella
 */
public class ProofTest {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {

        ElGamalKeyPair p = new ElGamalKeyPair();
        CyclicGroupParameters param = CyclicGroupParameters.getInstance();
        SchnorrNIProof proof = SchnorrNIZKP.makeProof(p.getSecretKey(), p.getPublicKey(), param);
        System.out.println("Test with well formed proof: " + SchnorrNIZKP.checkProof(proof, p.getPublicKey(), param));
        
        SchnorrNIProof fakeProof = SchnorrNIZKP.makeProof(new BigInteger(p.getSecretKey().bitLength(), new SecureRandom()), p.getPublicKey(), param);
        System.out.println("Test with bad formed proof: " + SchnorrNIZKP.checkProof(fakeProof, p.getPublicKey(), param));
    }

}
