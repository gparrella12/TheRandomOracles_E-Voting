package src.SchnorrNIZPK;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;
import src.ElGamalHomomorphic.CyclicGroupParameters;

/**
 *
 * @author gparrella
 */
public class SchnorrNIZPK {

    public static SchnorrNIProof makeProof(BigInteger x, BigInteger y, CyclicGroupParameters param) {
        int securityParameter = param.getSECURITY_PARAMETER().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        BigInteger r = new BigInteger(securityParameter, new SecureRandom()); // r random
        BigInteger a = g.modPow(r.mod(q), p); // a = g^r mod p

        BigInteger toHash = new BigInteger(y.toString().concat(a.toString()));
        BigInteger c = sha256(toHash);

        // z = r+c*x
        BigInteger z = r.add(c.multiply(x)).mod(q);

        return new SchnorrNIProof(a, c, z);

    }

    public static boolean verityProof(SchnorrNIProof proof, BigInteger y, CyclicGroupParameters param) {
        BigInteger g = param.getG();
        BigInteger p = param.getP();
        BigInteger a = proof.getA();
        BigInteger c = proof.getC();
        BigInteger z = proof.getZ();
        BigInteger q = param.getQ();
        BigInteger toHash = new BigInteger(y.toString().concat(a.toString()));
        BigInteger c1 = sha256(toHash);
        if (c.subtract(c1) == BigInteger.ZERO) {
            // k = g^z mod p
            BigInteger k = g.modPow(z.mod(q), p);
            // a * y^c mod p
            BigInteger res = a.multiply(y.modPow(c.mod(q), p)).mod(p);
            //System.out.println(k);
            //System.out.println(res);
            return k.subtract(res) == BigInteger.ZERO;
        } else {
            return false;
        }
    }

    private static String BytesToHex(byte[] b) {
        return HexFormat.of().formatHex(b);
    }

    private static BigInteger sha256(BigInteger input) {
        MessageDigest hashFunction = null;
        try {
            hashFunction = MessageDigest.getInstance("SHA256");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        // c1 = H(y || a), con y=g^x mod p        
        byte digest[] = new byte[hashFunction.getDigestLength()];
        digest = hashFunction.digest(input.toByteArray());
        //System.out.println(BytesToHex(digest));
        return new BigInteger(digest);

    }
}
