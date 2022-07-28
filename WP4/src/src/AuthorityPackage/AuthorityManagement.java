package src.AuthorityPackage;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import src.ElGamalHomomorphic.CyclicGroupParameters;
import src.ElGamalHomomorphic.ElGamalCipherText;
import src.Voter.*;

/**
 *
 * @author Ernesto
 */
public class AuthorityManagement {

    private static AuthorityManagement single_instance = null;
    private List<Authority> authorityList;
    private BigInteger votingKey;

    //metodo per implementare il singleton.
    public static AuthorityManagement getInstance() {
        if (single_instance == null) {
            single_instance = new AuthorityManagement();
        }

        return single_instance;
    }

    //inizializzo la lista inserendo le 20 regioni + il ministero
    private AuthorityManagement() {
        authorityList = new ArrayList<>();
        for (AuthorityName value : AuthorityName.values()) {
            authorityList.add(new Authority(value.toString()));
        }

    }

    public BigInteger getVotingKey() {
        return this.votingKey;
    }

    //recall: (a mod p) * (b mod p) = (a*b) mod p
    private void generateVotingKey() {
        CyclicGroupParameters c = new CyclicGroupParameters();
        BigInteger p = c.getP();

        votingKey = BigInteger.ONE;
        for (Authority a : authorityList) {
            votingKey = votingKey.multiply(a.getPublicEncKey());
        }

        this.votingKey = votingKey.mod(p);
    }

    public void validateVote(Voter voter, Vote v) {
    }

    public static ElGamalCipherText simulate(BigInteger pk_vote) {
        CyclicGroupParameters c = new CyclicGroupParameters();
        BigInteger p = c.getP();
        BigInteger g = c.getG();
        BigInteger q = c.getQ();
        int securityParameter = c.getSECURITY_PARAMETER().intValue();

        SecureRandom sc = new SecureRandom();
        BigInteger r = new BigInteger(securityParameter, sc);
        BigInteger vote = BigInteger.TEN;

        //g^R
        BigInteger u = g.modPow(r.mod(q), p);

        //g^M
        BigInteger z1 = g.modPow(vote.mod(q), p);

        //PK^SK
        BigInteger z2 = pk_vote.modPow(r.mod(q), p);

        //z = (g^M) * (PK^SK)
        BigInteger z = z1.multiply(z2).mod(p);

        return new ElGamalCipherText(u, z);

    }

    public void votesDecryption() {
        List<BigInteger> d = new ArrayList<>();
        CyclicGroupParameters cp = new CyclicGroupParameters();
        BigInteger g = cp.getG();
        BigInteger p = cp.getP();
        BigInteger q = cp.getQ();
       
        generateVotingKey();

        ElGamalCipherText c = simulate(votingKey);
        BigInteger u = c.getU();
        BigInteger z = c.getV();

        for (Authority a : authorityList) {
            d.add(u.modPow(a.getPrivateEncKey().mod(q), p));
        }

        BigInteger prod_d = BigInteger.ONE;
        for (BigInteger b : d) {
            prod_d = prod_d.multiply(b);
        }
        prod_d = prod_d.mod(p);

        BigInteger prod_d_inverse = prod_d.modInverse(p);

        BigInteger semiResult = prod_d_inverse.multiply(z).mod(p);

        BigInteger result = new BigInteger("0");
        while (true) {
            if (g.modPow(result, p).compareTo(semiResult) == 0) {
                break;
            }
            result = result.add(BigInteger.ONE);
        }
        System.out.println("voti Omega_1 = " + result);

    }
}
