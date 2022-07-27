package src.ElGamalHomomorphic;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.*;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import src.Authority.Authority;

/**
 *
 * @author Ernesto
 */
public class ElGamalKeyPair {

    private BigInteger secretKey;
    private BigInteger publicKey;

    public ElGamalKeyPair() {

        ElGamalParameters param = new ElGamalParameters();
        // Genera un elemento random di SECURITY_PARAMETER bit
        secretKey = new BigInteger(param.getSECURITY_PARAMETER().intValue(), new SecureRandom());
        // PK = G^SK mod P
        publicKey = param.getG().modPow(secretKey, param.getP());

    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getSecretKey() {
        return secretKey;
    }

    //test
    public static void main(String[] args) {
        Authority a1 = new Authority("Giuan");

        BigInteger M1, M2;          // Encryption done by Bob and Charlie
        M1 = new BigInteger("69");  // Bob encrypts 24
        M2 = new BigInteger("12");  // Charlie encrypts 14
        System.out.println("M1: " + M1.intValue());
        System.out.println("M2: " + M2.intValue());
        ElGamalKeyPair pair = new ElGamalKeyPair();
        ElGamalParameters param = new ElGamalParameters();
        ElGamalCipherText ct1 = ExponentialElGamal.encrypt(param, pair.getPublicKey(), M1);
        ElGamalCipherText ct2 = ExponentialElGamal.encrypt(param, pair.getPublicKey(), M2);
        
        System.out.println("C1: " + ct1.toString());
        System.out.println("C2: " + ct2.toString());
        
        ElGamalCipherText res = ExponentialElGamal.aggregate(param, ct1, ct2);
        
        System.out.println("C3: " + res.toString());
        
        BigInteger plainTextResult = ExponentialElGamal.decrypt(param, res, pair.getSecretKey());

        System.out.println("The result is: " + plainTextResult.intValue());
    }
}
