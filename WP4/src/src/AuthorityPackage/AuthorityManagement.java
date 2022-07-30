package src.AuthorityPackage;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import src.ElGamalHomomorphic.CyclicGroupParameters;
import src.ElGamalHomomorphic.ElGamalCipherText;
import src.VoterPackage.*;

/**
 *
 * @author Ernesto
 */
public class AuthorityManagement implements Serializable {

    private static AuthorityManagement single_instance = null;
    private List<Authority> authorityList;
    private BigInteger votingKey;

    //Singleton method

    /**
     *
     * @return
     */
    public static AuthorityManagement getInstance() {
        if (single_instance == null) {
            single_instance = new AuthorityManagement();
        }

        return single_instance;
    }

    //Initialization of autorithy list with 20 regions + minister   
    private AuthorityManagement() {
        authorityList = new ArrayList<>();
        for (AuthorityName value : AuthorityName.values()) {
            authorityList.add(new Authority(value.toString()));
        }

    }

    /**
     *
     * @return
     */
    public BigInteger getVotingKey() {
        return this.votingKey;
    }

    /**
     *
     */
    public void generateVotingKey() {
        CyclicGroupParameters c = new CyclicGroupParameters();
        BigInteger p = c.getP();

        votingKey = BigInteger.ONE;
        for (Authority a : authorityList) {
            votingKey = votingKey.multiply(a.getPublicEncKey());
        }

        this.votingKey = votingKey.mod(p);
    }

    /**
     *
     * @param voter
     * @param vote
     * @param vp
     * @param signVote
     * @return
     */
    public boolean validateVote(Voter voter, Vote vote, VoteProof vp, byte[] signVote) {
        try {
            Signature signature = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());

            signature.initVerify(voter.getCertificate());

            signature.update(vote.toString().concat(vp.toString()).getBytes()); // for verification use update+verify methods, first calling update with the message and the calling verify with the signature

            return signature.verify(signVote);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     *
     * @param pk_vote
     * @return
     */
    public static ElGamalCipherText simulate(BigInteger pk_vote) {
        CyclicGroupParameters c = new CyclicGroupParameters();
        BigInteger p = c.getP();
        BigInteger g = c.getG();
        BigInteger q = c.getQ();
        int securityParameter = c.getSecurityParameter().intValue();

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

    /**
     *
     * @param c
     * @return
     */
    public BigInteger votesDecryption(ElGamalCipherText c) {
        List<BigInteger> d = new ArrayList<>();
        CyclicGroupParameters cp = new CyclicGroupParameters();
        BigInteger g = cp.getG();
        BigInteger p = cp.getP();
        BigInteger q = cp.getQ();

        // ElGamalCipherText c = simulate(votingKey);
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

        return result;

    }

    /**
     *
     * @return
     */
    public List<Authority> getAuthorityList() {
        return authorityList;
    }

}
