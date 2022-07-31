package src.CryptographicTools.SchnorrNIZPK;

import java.math.BigInteger;
import java.security.SecureRandom;
import src.CryptographicTools.CryptographicHash;
import src.CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;

/**
 * This Class contains some static methods to implements the Schnorr NIZKP.
 *
 * @author gparrella
 */
public class SchnorrNIZKP {

    /**
     * This method allows to make the Schnorr Non-interactive Zero-Knowledge
     * Proof.
     *
     * The claim of the proof is the following: d<sub>A<sub>i</sub>, voti
     * <sub>&Omega;<sub>1</sub></sub></sub>
     * = (g<sup>r</sup>)<sup>x<sub>A<sub>i</sub></sub></sup>
     * &and; PK<sub>A<sub>i</sub></sub>
     * =
     * g<sup>x<sub>A<sub>i</sub></sub></sup>
     *
     * @param x the secrete key associated to y
     * @param y the public key, y=g^x
     * @param param the parameters of a cyclic group of order q
     * @return a <code> SchnorrNIProof </code> object
     */
    public static SchnorrNIProof makeProof(BigInteger x, BigInteger y, CyclicGroupParameters param) {
        int securityParameter = param.getSecurityParameter().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        // r random
        BigInteger r = new BigInteger(securityParameter, new SecureRandom());

        // a = g^r mod p
        BigInteger a = g.modPow(r.mod(q), p);

        BigInteger toHash = new BigInteger(y.toString().concat(a.toString()));
        BigInteger c = new BigInteger(CryptographicHash.hash(toHash.toByteArray())); // c = H(y || a), con y=g^x mod p  

        // z = r+c*x
        BigInteger z = r.add(c.multiply(x)).mod(q);

        return new SchnorrNIProof(a, c, z);
    }

    /**
     * This method checks if a Schnorr NIZKP is valid with respect to a public
     * key y.
     *
     * @param proof the proof, represented by a <code>SchnorrNIProof</code>
     * object
     * @param y the public key, y=g^x
     * @param param the parameters of a cyclic group of order q
     * @return true if the NIZKP is valid, false otherwise
     */
    public static boolean checkProof(SchnorrNIProof proof, BigInteger y, CyclicGroupParameters param) {
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

        // c1 = H(y || a), con y=g^x mod p
        BigInteger c1 = new BigInteger(CryptographicHash.hash(toHash.toByteArray()));

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

}
