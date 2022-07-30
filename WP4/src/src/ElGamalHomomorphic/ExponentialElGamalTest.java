package src.ElGamalHomomorphic;

import java.math.BigInteger;
import src.AuthorityPackage.Authority;

/**
 *
 * @author fsonnessa
 */
public class ExponentialElGamalTest {

    /**
     * @param args the command line arguments
     */
    //test
    public static void main(String[] args) {
        Authority a1 = new Authority("Campania");

        BigInteger M1, M2;          // Encryption done by Bob and Charlie
        M1 = new BigInteger("24");  // Bob encrypts 24
        M2 = new BigInteger("12");  // Charlie encrypts 14

        System.out.println("M1: " + M1.intValue());
        System.out.println("M2: " + M2.intValue());

        ElGamalKeyPair pair = new ElGamalKeyPair();
        CyclicGroupParameters param = new CyclicGroupParameters();
        ElGamalCipherText ct1 = ExponentialElGamal.encrypt(param, pair.getPublicKey(), M1);
        ElGamalCipherText ct2 = ExponentialElGamal.encrypt(param, pair.getPublicKey(), M2);

        System.out.println("C1: " + ct1.toString());
        System.out.println("C2: " + ct2.toString());

        ElGamalCipherText res = ExponentialElGamal.aggregate(param, ct1, ct2);

        System.out.println("C3: " + res.toString());

        BigInteger plainTextResult = ExponentialElGamal.decrypt(param, res, pair.getSecretKey());

        System.out.println("The result is: " + plainTextResult.intValue());
/*
        System.out.println("aggregate");

        for (int i = -5; i < 5; i++) {
            BigInteger m = new BigInteger(String.valueOf(i));
            int i2 = - i * 6 + 1;
            BigInteger m1 = new BigInteger(String.valueOf(i2));

            ElGamalCipherText c1 = ExponentialElGamal.encrypt(param, pair.getPublicKey(), m);
            ElGamalCipherText c2 = ExponentialElGamal.encrypt(param, pair.getPublicKey(), m1);
            ElGamalCipherText c3 = ExponentialElGamal.aggregate(param, c1, c2);

            if (!c3.getU().equals(c1.getU().multiply(c2.getU()).mod(param.getP()))) {
                System.err.println("error with u values");
            }
            if (!c3.getV().equals(c1.getV().multiply(c2.getV()).mod(param.getP()))) {
                System.err.println("error with v values");
            }

            plainTextResult = ExponentialElGamal.decrypt(param, c3, pair.getSecretKey());

            if (plainTextResult.intValue() != (i + i2)) {
                System.err.println("decrypt error");
            }
        }*/
    }

}
