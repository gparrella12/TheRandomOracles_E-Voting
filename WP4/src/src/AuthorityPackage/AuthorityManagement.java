package src.AuthorityPackage;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import src.ElGamalHomomorphic.CyclicGroupParameters;
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
     * @return
     */
    public List<Authority> getAuthorityList() {
        return authorityList;
    }

}
