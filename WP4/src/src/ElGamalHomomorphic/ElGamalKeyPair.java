package src.ElGamalHomomorphic;

import java.math.BigInteger;
import java.security.SecureRandom;
/**
 *
 * @author Ernesto
 */
public class ElGamalKeyPair {

    private final BigInteger secretKey;
    private final BigInteger publicKey;

    public ElGamalKeyPair() {

        CyclicGroupParameters param = new CyclicGroupParameters();
        int securityParameter = param.getSECURITY_PARAMETER().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();
        // Genera un elemento random di SECURITY_PARAMETER bit
        // SK <- Z_q, quindi selezionato l'elemento, si applica il modulo q
        this.secretKey = new BigInteger(securityParameter , new SecureRandom()).mod(q);
        // PK = G^SK mod P
        this.publicKey = g.modPow(secretKey, p);

    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getSecretKey() {
        return secretKey;
    }
}
