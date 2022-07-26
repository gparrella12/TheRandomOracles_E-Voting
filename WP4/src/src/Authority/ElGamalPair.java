package src.Authority;
import java.math.*;
import java.security.*;

/**
 *
 * @author Ernesto
 */

public class ElGamalPair {
    //Per velocità 5, poi andrà settato a 2048.
    private static int SECURITY_PARAMETER=5;
    private BigInteger g, p, q;
    private int securityparameter;
    private BigInteger skValue;
    private BigInteger pkValue;
    private static ElGamalPair single_instance = null;
        //metodo per implementare il singleton.
    
    public static ElGamalPair getInstance() {
        if (single_instance == null) {
            single_instance = new ElGamalPair();
        }

        return single_instance;
    }
    
    private ElGamalPair() {
        BigInteger p, q, g, pk, sk;

        SecureRandom sr = new SecureRandom();

        while (true) {
            q = BigInteger.probablePrime(SECURITY_PARAMETER, sr);
            p = q.multiply(BigInteger.TWO);
            p = p.add(BigInteger.ONE);

            if (p.isProbablePrime(50) == true) {
                break;
            }

        }

        g = new BigInteger("4");
        sk = new BigInteger(SECURITY_PARAMETER, sr);
        pk = g.modPow(sk, p);

        this.g = g;
        this.p = p;
        this.q = q;
        this.skValue = sk;
        this.pkValue = pk;

    }

    public BigInteger getPkValue() {
        return pkValue;
    }

    public BigInteger getSkValue() {
        return skValue;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public static int getSECURITY_PARAMETER() {
        return SECURITY_PARAMETER;
    }

}
