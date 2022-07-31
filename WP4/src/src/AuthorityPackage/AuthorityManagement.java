package src.AuthorityPackage;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import src.CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import src.CryptographicTools.SignatureScheme;
import src.VoterPackage.*;

/**
 * This class represents the authorities and the activities they perform.
 *
 * @author Ernesto
 */
public class AuthorityManagement implements Serializable {

    private static AuthorityManagement single_instance = null;
    private final List<Authority> authorityList;
    private BigInteger votingKey = null;

    /**
     * This method is for applying the singleton patter design. The reason for
     * this choice is that you want to instantiate only an object of this class,
     * as the opposite would not make sense.
     *
     * @return the instance of this class.
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
     * This method return the voting key, i.e. PK<sub>voting</sub>
     *
     * @return a <code>BigInteger</code> representing the PK<sub>voting</sub>
     */
    public BigInteger getVotingKey() {
        return this.votingKey;
    }

    /**
     * This method generates the PK<sub>voting</sub>, where:
     *
     * PK<sub>voting</sub>
     * = &#928 <sub> i=1 </sub><sup>N <sub>a</sub> </sup>
     * (PK<sub>A<sub>i</sub></sub>)
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
     * This method validates the vote checking if the signature it receives as
     * input, that is <code>signVote</code>, is the same as the one it
     * calculates by concatenating the vote and its proof, that is:
     *
     * signVote == SIG(vote || vote's proof)
     *
     * @param voter
     * @param vote of the voter
     * @param vp, proof of validity of the vote
     * @param signVote, signature of (vote || vote's proof)
     * @return a <code>boolean</code> that is true if the vote is valid,
     * otherwise false
     */
    public boolean validateVote(Voter voter, Vote vote, VoteProof vp, byte[] signVote) {
        return SignatureScheme.verifySignature(voter.getPublicSigKey(), vote.toString().concat(vp.toString()).getBytes(), signVote);
    }

    /**
     * This method returns the list containing all the authorities.
     *
     * @return a <code>List</code> object containing all the authorities.
     */
    public List<Authority> getAuthorityList() {
        return authorityList;
    }

}
