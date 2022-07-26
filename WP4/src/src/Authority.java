package src;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Authority {

    private String name;

    private final Key privateEncKey;

    private final Key publicEncKey;

    private final Key privateSigKey;

    private final Key publicSigKey;

    private Object authorityCertificate;

    public Authority(String name) {
        this.name = name;
        
        SecureRandom random = new SecureRandom();

        KeyPairGenerator generatorEnc = null;
        KeyPairGenerator generatorSig = null;
        try {
            generatorEnc = KeyPairGenerator.getInstance("ElGamal"); // generatorEnc of the keys
            generatorSig = KeyPairGenerator.getInstance("ECDSA"); // generatorSig of the keys
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Authority.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
        // VERIFICARE IL PARAMETRO DA INSERIRE SUCCESSIVAMENTE
        generatorEnc.initialize(2048, random); // initialize with 2048 bits of security with a secure random source

        KeyPair pair = generatorEnc.generateKeyPair(); // generate a pair of PK/SK 
        this.publicEncKey = pair.getPublic(); // method getPublic returns PK
        this.privateEncKey = pair.getPrivate(); // getPrivate SK
  
        pair = generatorSig.generateKeyPair(); // generate a pair of PK/SK 
        this.publicSigKey = pair.getPublic(); // method getPublic returns PK
        this.privateSigKey = pair.getPrivate(); // getPrivate SK       
    }

    public String getName() {
        return name;
    }

    public Key getPublicEncKey() {
        return publicEncKey;
    }

    public Key getPublicSigKey() {
        return publicSigKey;
    }

    public Object getAuthorityCertificate() {
        return authorityCertificate;
    }

    @Override
    public String toString() {
        return "Authority{" + "name=" + name + ", publicEncKey=" + publicEncKey + ", publicSigKey=" + publicSigKey + '}';
    }

    
    
}
