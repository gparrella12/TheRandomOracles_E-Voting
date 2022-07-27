package src.AuthorityPackage;

import src.ElGamalHomomorphic.ElGamalKeyPair;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ernesto
 */
public class Authority {

    private String name;
    private final BigInteger privateEncKey;
    private final BigInteger publicEncKey;
    private final Key privateSigKey;
    private final Key publicSigKey;
    private Object certificate;

    //nel costruttore creo la coppia (sk,pk);   
    public Authority(String name) {
        this.name = name;
        ElGamalKeyPair encPair = new ElGamalKeyPair();
        this.privateEncKey = encPair.getSecretKey();
        this.publicEncKey = encPair.getPublicKey();

        SecureRandom random = new SecureRandom();
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("ECDSA"); // generator of the keys
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        // with a secure random source
        KeyPair sigPair = generator.generateKeyPair(); // generate a encPair of PK/SK 
        this.publicSigKey = sigPair.getPublic(); // method getPublic returns PK
        this.privateSigKey = sigPair.getPrivate(); // getPrivate SK

        this.certificate = null;
    }

    public Key getPrivateSigKey() {
        return privateSigKey;
    }

    public BigInteger getPublicEncKey() {
        return publicEncKey;
    }

    public BigInteger getPrivateEncKey() {
        return privateEncKey;
    }

    public Object getPublicSigKey() {
        return publicSigKey;
    }

    public Object getCertificate() {
        return certificate;
    }

}
