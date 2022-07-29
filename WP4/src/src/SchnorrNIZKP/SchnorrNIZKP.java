package src.SchnorrNIZKP;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import src.ElGamalHomomorphic.CyclicGroupParameters;

/**
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
        int securityParameter = param.getSECURITY_PARAMETER().intValue();
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
     *
     * @param proof
     * @param y
     * @param param
     * @return
     */
    public static boolean verityProof(SchnorrNIProof proof, BigInteger y, CyclicGroupParameters param) {
        BigInteger g = param.getG();
        BigInteger p = param.getP();
        BigInteger q = param.getQ();

        BigInteger a = proof.getA();
        BigInteger c = proof.getC();
        BigInteger z = proof.getZ();

        BigInteger toHash = new BigInteger(y.toString().concat(a.toString()));
        BigInteger c1 = hash(toHash); // c1 = H(y || a), con y=g^x mod p

        if (c.subtract(c1) == BigInteger.ZERO) {
            // k = g^z mod p
            BigInteger k = g.modPow(z.mod(q), p);
            // a * y^c mod p
            BigInteger res = a.multiply(y.modPow(c.mod(q), p)).mod(p);
            //System.out.println(k);
            //System.out.println(res);
            return k.subtract(res) == BigInteger.ZERO;
        }

        return false;
    }

    private static BigInteger hash(BigInteger input) {
        MessageDigest hashFunction = null;
        try {
            hashFunction = MessageDigest.getInstance(getHashFunction());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        byte digest[] = new byte[hashFunction.getDigestLength()];
        digest = hashFunction.digest(input.toByteArray());
        //System.out.println(BytesToHex(digest));

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
