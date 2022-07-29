package src.ElGamalHomomorphic;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author gparrella
 */
public class ExponentialElGamal {

    public static ElGamalCipherText encrypt(CyclicGroupParameters param, BigInteger pk, BigInteger m) {
        int securityParameter = param.getSECURITY_PARAMETER().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        SecureRandom sc = new SecureRandom();

        BigInteger M = g.modPow(m.mod(q), p);
        BigInteger r = new BigInteger(securityParameter, sc);

        BigInteger u = g.modPow(r.mod(q), p);
        BigInteger v = M.multiply(pk.modPow(r.mod(q), p));

        return new ElGamalCipherText(u, v);

    }

    public static BigInteger decrypt(CyclicGroupParameters param, ElGamalCipherText cipherText, BigInteger sk) {
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        // tmp = (u^sk mod p)^-1 mod p 
        BigInteger tmp = cipherText.getU().modPow(sk.mod(q), p).modInverse(p);
        
        // res = tmp*v mod p = (u^sk mod p)^-1 mod p * v mod p
        BigInteger res = tmp.multiply(cipherText.getV()).mod(p);

        // Bruteforce
        BigInteger M = new BigInteger("0");
        while (true) {
            // g^M mod p == res con M=0,1,2,....
            if (g.modPow(M, p).compareTo(res) == 0) {
                return M;
            }
            M = M.add(BigInteger.ONE);
        }

    }

    public static ElGamalCipherText aggregate(CyclicGroupParameters param, ElGamalCipherText cipherText1, ElGamalCipherText cipherText2) {
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();
        BigInteger newU = cipherText1.getU().multiply(cipherText2.getU()).mod(p);
        BigInteger newV = cipherText1.getV().multiply(cipherText2.getV()).mod(p);
        return new ElGamalCipherText(newU, newV);
    }
}
