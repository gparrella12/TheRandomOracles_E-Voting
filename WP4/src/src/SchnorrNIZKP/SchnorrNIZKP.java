package src.SchnorrNIZKP;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import src.ElGamalHomomorphic.CyclicGroupParameters;

/**
 * This class contains some static function to implements the Schnorr's NIZKP.
 *
 * @author gparrella
 */
public class SchnorrNIZKP {

    private static String hashFunction = "SHA256";

    /**
     *
     * @param x
     * @param y
     * @param param
     * @return
     */
    public static SchnorrNIProof makeProof(BigInteger x, BigInteger y, CyclicGroupParameters param) {
        int securityParameter = param.getSecurityParameter().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        BigInteger r = new BigInteger(securityParameter, new SecureRandom()); // r random
        BigInteger a = g.modPow(r.mod(q), p); // a = g^r mod p

        BigInteger toHash = new BigInteger(y.toString().concat(a.toString()));
        BigInteger c = hash(toHash); // c = H(y || a), con y=g^x mod p  

        // z = r+c*x
        BigInteger z = r.add(c.multiply(x)).mod(q);

        return new SchnorrNIProof(a, c, z);
    }

    /**
     * This function verify a NIZKP of Schnorr.
     *
     * @param proof the values of the NIZKP
     * @param y the public key
     * @param param the parameters of cyclic group of order q
     * @return true if ZKP is verified, false otherwise
     */
    public static boolean verityProof(SchnorrNIProof proof, BigInteger y, CyclicGroupParameters param) {
        // Get the parameters used
        BigInteger g = param.getG();
        BigInteger p = param.getP();
        BigInteger q = param.getQ();
        // Get the value of the NIZKP
        BigInteger a = proof.getA();
        BigInteger c = proof.getC();
        BigInteger z = proof.getZ();
        // Compute the digest of y || a
        BigInteger toHash = new BigInteger(y.toString().concat(a.toString()));
        BigInteger c1 = hash(toHash); // c1 = H(y || a), con y=g^x mod p
        // Verify if c value is equal to H(y || a) previously computed
        if (c.equals(c1)) {
            // compute k = g^z mod p
            BigInteger k = g.modPow(z.mod(q), p);
            // compute a * y^c mod p
            BigInteger res = a.multiply(y.modPow(c.mod(q), p)).mod(p);
            // verify if a*y^c = g^z
            return k.equals(res);
        }
        return false;
    }

    private static BigInteger hash(BigInteger input) {
        MessageDigest h = null;
        try {
            h = MessageDigest.getInstance(getHashFunction(), new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        byte digest[] = new byte[h.getDigestLength()];
        digest = h.digest(input.toByteArray());

        return new BigInteger(digest);

    }

    /**
     *
     * @return
     */
    public static String getHashFunction() {
        return hashFunction;
    }

    /**
     *
     * @param hashFunction
     */
    public static void setHashFunction(String hashFunction) {
        SchnorrNIZKP.hashFunction = hashFunction;
    }
}
