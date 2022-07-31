package src.CryptographicTools.ElGamalHomomorphic;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class contains some static methods that allow you 
 * to encrypt and decrypt a message using the Exponential ElGamal Scheme.
 *
 * @author gparrella
 */
public class ExponentialElGamal {

    /**
     * This method encrypts message using the Exponential ElGamal Scheme.
     *
     * @param param the parameters of the cyclic group of order q used.
     * @param pk the public key needed to encrypt the message
     * @param m the message to encrypt
     * @return an ElGamalCipherText of the message
     */
    public static ElGamalCipherText encrypt(CyclicGroupParameters param, BigInteger pk, BigInteger m) {
        // Get the parameters of the cyclic group of order q.
        int securityParameter = param.getSecurityParameter().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();
        
        // Get a secure source of randomness
        SecureRandom sc = new SecureRandom();

        // r <- Z_q, r randomly chosen in Z_q
        BigInteger r = new BigInteger(securityParameter, sc).mod(q);
        
        // u = g^r mod p 
        BigInteger u = g.modPow(r, p);
        
        // v = g^m * pk^r mod p
        BigInteger v = g.modPow(m.mod(q), p).multiply(pk.modPow(r, p));

        return new ElGamalCipherText(u, v);

    }

    /**
     * This method decrypt message previously encrypted using 
     * the Exponential ElGamal Scheme.
     *
     * @param param the parameters of the cyclic group of order q used.
     * @param cipherText the ciphertext to decrypt
     * @param sk the secret key needed to decrypt the ciphertext
     * @return the decrypted message
     */
    public static BigInteger decrypt(CyclicGroupParameters param, ElGamalCipherText cipherText, BigInteger sk) {
        // Get the parameters used
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        // tmp = (u^sk mod p)^-1 mod p 
        BigInteger tmp = cipherText.getU().modPow(sk.mod(q), p).modInverse(p);

        // res = tmp*v mod p = (u^sk mod p)^-1 mod p * v mod p
        BigInteger res = tmp.multiply(cipherText.getV()).mod(p);

        // Bruteforce to find the m value
        BigInteger m = new BigInteger("0");
        while (true) {
            // g^m mod p == res with m=0,1,2,....
            if (g.modPow(m, p).compareTo(res) == 0) {
                return m;
            }
            m = m.add(BigInteger.ONE);
        }

    }

    /**
     * This method aggregates two Exponential ElGama Ciphertext, 
     * to exploit the multiplicative homomorphic property of the scheme.
     *
     * @param param the parameters of the cyclic group of order q used.
     * @param cipherText1 the 1st ciphertext
     * @param cipherText2 the 2nd ciphertext
     * @return the aggregate ciphertext
     */
    public static ElGamalCipherText aggregate(CyclicGroupParameters param, ElGamalCipherText cipherText1, ElGamalCipherText cipherText2) {
        // Get the parameters used
        BigInteger p = param.getP();
        
        // Compute the new u and v values by multiplying the corresponding values of the ciphertexts
        BigInteger newU = cipherText1.getU().multiply(cipherText2.getU()).mod(p);
        BigInteger newV = cipherText1.getV().multiply(cipherText2.getV()).mod(p);
        return new ElGamalCipherText(newU, newV);
    }
}
