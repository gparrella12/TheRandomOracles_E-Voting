package src.Authority;
import java.math.*;
import java.security.*;
import java.util.List;

/**
 *
 * @author Ernesto
 */
public class ElGamalPair {

    private BigInteger g, p, q;
    private BigInteger SECURITY_PARAMETER;
    private BigInteger skValue;
    private BigInteger pkValue;

    public static void print(Object o) {
        System.out.println(o);
    }

    public ElGamalPair() {
        List<BigInteger> schemeParameters = ElGamalParameters.getParameters();
        SECURITY_PARAMETER = schemeParameters.get(0);
        g = schemeParameters.get(1);
        p = schemeParameters.get(2);
        q = schemeParameters.get(3);
        SecureRandom sr = new SecureRandom();
        skValue = new BigInteger(SECURITY_PARAMETER.intValue(), sr);
        pkValue = g.modPow(skValue, p);

    }

    public BigInteger getPkValue() {
        return pkValue;
    }

    public BigInteger getSkValue() {
        return skValue;
    }

    public static ElGamalCT EncryptInTheExponent(BigInteger pk, BigInteger m) {
        SecureRandom sc = new SecureRandom();
        BigInteger p = ElGamalParameters.getP();
        BigInteger g = ElGamalParameters.getG();
        int securityParameter = ElGamalParameters.getSECURITY_PARAMETER().intValue();

        BigInteger M = g.modPow(m, p);
        BigInteger r = new BigInteger(securityParameter, sc);
        BigInteger C = M.multiply(pk.modPow(r, p)).mod(p);
        BigInteger C2 = g.modPow(r, p);
        return new ElGamalCT(C, C2);

    }

    public static BigInteger DecryptInTheExponent(ElGamalCT CT, BigInteger sk) {
        BigInteger p = ElGamalParameters.getP();
        BigInteger g = ElGamalParameters.getG();

        BigInteger tmp = CT.C2.modPow(sk, p).modInverse(p);

        BigInteger res = tmp.multiply(CT.C).mod(p);

        BigInteger M = new BigInteger("0");
        while (true) {
            if (g.modPow(M, p).compareTo(res) == 0) {
                return M;
            }

            M = M.add(BigInteger.ONE);
        }

    }

    public static ElGamalCT Homomorphism(ElGamalCT CT1, ElGamalCT CT2) {
        BigInteger p = ElGamalParameters.getP();
        ElGamalCT CT = new ElGamalCT(CT1);        // CT=CT1
        CT.C = CT.C.multiply(CT2.C).mod(p);       // CT.C=CT.C*CT2.C mod p
        CT.C2 = CT.C2.multiply(CT2.C2).mod(p);    // CT.C2=CT.C2*CT2.C2 mod p
        return CT; // If CT1 encrypts m1 and CT2 encrypts m2 then CT encrypts m1+m2

    }

    
    //test
    public static void main(String[] args) {
        Authority a1 = new Authority("Giuan");

        BigInteger M1, M2;          // Encryption done by Bob and Charlie
        M1 = new BigInteger("24");  // Bob encrypts 24
        M2 = new BigInteger("14");  // Charlie encrypts 14

        ElGamalCT CT1 = EncryptInTheExponent(a1.getPublicEncKey(), M1); // CT1 encrypts 24     
        print(a1.getPublicEncKey());
        print(a1.getPrivateEncKey());
        print(CT1.C);
        ElGamalCT CT2 = EncryptInTheExponent(a1.getPublicEncKey(), M2); // CT2 encrypts 14     

        ElGamalCT CTH = Homomorphism(CT1, CT2); // CTH encrypts the sum of the plaintexts in CT1 and CT2 that is 24+14

        // Alice receives CTH and wants to decrypt
        BigInteger D;
        D = DecryptInTheExponent(CTH, a1.getPrivateEncKey());

        System.out.println("decrypted plaintext with Exponential El Gamal= " + D); // it should be 38

    }
}
