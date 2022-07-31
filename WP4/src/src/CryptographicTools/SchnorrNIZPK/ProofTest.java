package src.CryptographicTools.SchnorrNIZPK;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import src.CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import src.CryptographicTools.ElGamalHomomorphic.ElGamalKeyPair;

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
        CyclicGroupParameters param = new CyclicGroupParameters();
        SchnorrNIProof proof = SchnorrNIZKP.makeProof(p.getSecretKey(), p.getPublicKey(), param);
        System.out.println("Test with well formed proof: " + SchnorrNIZKP.verityProof(proof, p.getPublicKey(), param));
        
        SchnorrNIProof fakeProof = SchnorrNIZKP.makeProof(new BigInteger(p.getSecretKey().bitLength(), new SecureRandom()), p.getPublicKey(), param);
        System.out.println("Test with bad formed proof: " + SchnorrNIZKP.verityProof(fakeProof, p.getPublicKey(), param));
    }

}
