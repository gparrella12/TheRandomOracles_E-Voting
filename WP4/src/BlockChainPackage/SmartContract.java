package BlockChainPackage;

import AuthorityPackage.Authority;
import AuthorityPackage.AuthorityManagement;
import CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import CryptographicTools.ElGamalHomomorphic.ElGamalCipherText;
import CryptographicTools.ElGamalHomomorphic.ExponentialElGamal;
import CryptographicTools.SchnorrNIZKP.SchnorrNIProof;
import CryptographicTools.SchnorrNIZKP.SchnorrNIZKP;
import VoterPackage.Vote;
import VoterPackage.VoteProof;
import VoterPackage.Voter;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



/**
 * This class contains the implementation of a Smart Contract.
 *
 * @author franc
 */
public class SmartContract implements Serializable {

    private final BlockChain blockchain;
    private BigInteger votingKey;
    private final LocalDateTime startElection;
    private final LocalDateTime endElection;
    private final AuthorityManagement manager;
    private final List<ElGamalCipherText> votes;
    private BigInteger resultCandidate1 = null;
    private BigInteger resultCandidate2 = null;

    /**
     * Creates a smart contract by passing:
     * <ul>
     * <li><code>startElection, which defines the instant from which you can vote</code></li>
     * <li><code>endElection, which defines the instant from which you can not vote anymore</code></li>
     * <li><code>am, comprises the list authority and the activities they carry out</code></li>
     * <li><code>bcFilename, the name of the file used to simulate the blockchain</code></li>
     * </ul>
     *
     * @param startElection the instant from which you can vote
     * @param endElection the instant from which you can not vote anymore
     * @param am authority management
     * @param bcFilename the name of the file used to simulate the blockchain
     */
    public SmartContract(LocalDateTime startElection, LocalDateTime endElection, AuthorityManagement am, String bcFilename) {
        this.blockchain = new BlockChain(bcFilename, "blockchain_serial");
        this.startElection = startElection;
        this.endElection = endElection;
        this.manager = am;
        this.votes = new ArrayList<>();
    }

    /**
     * This method puts a block, on the blockchain, for each authority. This
     * block contains informations of the authority, such as:
     * <ul>
     * <li>the name</li>
     * <li>public encryption and signature keys</li>
     * <li>the certificate</li>
     * <li>the proof that the authority knows the secret encryption key
     * associated to its public encryption key</li>
     * </ul>
     *
     */
    public void blockChainInit() {
        for (Authority a : manager.getAuthorityList()) {
            SchnorrNIProof proof = SchnorrNIZKP.makeProof(a.getPrivateEncKey(), a.getPublicEncKey(), CyclicGroupParameters.getInstance());
            AuthorityBlock data = new AuthorityBlock(a, proof);
            blockchain.addBlock(new Block<>(data));
        }
    }

    /**
     * This method generates and put on the blockchain, the aggregate key of the
     * authorities, PK<sub>voting</sub>
     */
    public void keyGeneration() {
        this.manager.generateVotingKey();
        this.votingKey = this.manager.getVotingKey();
        blockchain.addBlock(new Block<>(this));
    }

    /**
     * This method adds on the blockchain a transaction that contains:   
     * <ul>     
     * <li> voter's vote </li>
     * <li> vote's proof </li>
     * <li> signature of the vote and its proof </li>
     * </ul>
     *
     * if and only if:
     * <ul>
     * <li> the time at which you vote is included in the voting window </li>
     * <li> the vote is valid with respect to the method
     * <code>validateVote</code> </li>
     * </ul>
     *
     * In addition, this method changes the attribute of the voter to take into
     * account that he or she has voted
     *
     * @param voter the voter
     * @param vote the voter's vote
     * @param vp the proof of the validity of the vote
     * @param signVote the signature of (vote || vp)
     */
    public void vote(Voter voter, Vote vote, VoteProof vp, byte[] signVote) {
        if (LocalDateTime.now().isBefore(endElection) && LocalDateTime.now().isAfter(startElection)) {
            if (!voter.hasVoted() && this.manager.validateVote(voter, vote, vp, signVote)) {
                this.votes.add(vote.getEncVote());
                VoteTransaction vt = new VoteTransaction(vote, vp, signVote);
                this.blockchain.addBlock(new Block<>(vt));
                voter.setVoted();
            } else {
                throw new RuntimeException("Not valid vote");
            }
        } else {
            throw new RuntimeException("TimeOut for vote");
        }
    }

    /**
     * This method aggregates the ciphertexts submitted by all the voters. The
     * aggregation will ouput a ciphertext whose underlying message represents
     * the total number of votes associated with the candidate Omega_1.
     *<br></br>
     * c<sub>voti<sub> &#937; <sub>1</sub></sub></sub> = &#928; <sub> i=1
     * </sub><sup>TN </sup> (c<sub>i</sub>) = (g<sup>R</sup>, g<sup>M</sup>
     * g<sup>RK</sup>)
     *
     * where:<br></br>
     * R=&sum;<sub>i=1</sub><sup>TN</sup>(r<sub>i</sub>)
     * M=&sum;<sub>i=1</sub><sup>TN</sup>(m<sub>i</sub>)
     * K=&sum;<sub>n=1</sub><sup>n<sub>a</sub></sup>(
     * x<sub>A<sub>n</sub></sub>)
     * TN= number of transactions.
     *<br></br>
     * This aggregation is made possible by the Additive Homomorphism
     * ElGamal Encryption Scheme.
     *
     * @return the aggregate ciphertext
     */
    public ElGamalCipherText aggregateCipherText() {
        ElGamalCipherText result = this.votes.get(0);
        for (int i = 1; i < this.votes.size(); i++) {
            result = ExponentialElGamal.aggregate(CyclicGroupParameters.getInstance(), result, this.votes.get(i));
        }

        this.blockchain.addBlock(new Block<ElGamalCipherText>(result));
        return result;
    }

    /**
     * This method counts the votes of the two candidates. In particular:
     * <ul>
     * <li> authority<sub>i</sub> computes: d<sub>A<sub>i</sub>,
     * voti<sub>&#937;<sub>1</sub></sub></sub>
     * = ( g<sup>R</sup>)<sup>x<sub>A<sub>i</sub></sub></sup> </li>
     *
     * <li>
     * voti<sub>&#937;<sub>1</sub></sub>
     * =
     * (g<sup>M</sup> g<sup>RK</sup>) (&#928; <sub> i=1 </sub>
     * <sup>N<sub>a</sub></sup>
     * (d<sub>A<sub>i</sub>,
     * voti<sub>&#937;<sub>1</sub></sub></sub>))<sup>-1</sup>
     * =(g<sup>M</sup> g<sup>RK</sup>)(g<sup>RK</sup>)<sup>-1</sup>
     * </li>
     *
     *
     * <li>
     * voti<sub>&#937;<sub>2</sub></sub> = TN -
     * voti<sub>&#937;<sub>1</sub></sub>, with TN= number of transactions
     * </li>
     * </ul>
     *
     *
     *
     * @param aggregatedCipherText the aggregate ciphertext
     */
    public void tallying(ElGamalCipherText aggregatedCipherText) {
        List<BigInteger> partialShares = new ArrayList<>();
        CyclicGroupParameters cp = CyclicGroupParameters.getInstance();
        BigInteger g = cp.getG();
        BigInteger p = cp.getP();
        BigInteger q = cp.getQ();

        //u = g^r, with r randomly chosen in Z_q
        BigInteger u = aggregatedCipherText.getU();
        //v = g^m * y^r, with y public key
        BigInteger z = aggregatedCipherText.getV();
        
        
        for (Authority a : manager.getAuthorityList()) {
            // for each Authority a compute the corresponding share
            BigInteger share = u.modPow(a.getPrivateEncKey().mod(q), p);
            // add the authority share to the others just collected
            partialShares.add(share);
            // create and add a new block associated to the partial share of the autority
            this.blockchain.addBlock(new Block<>(new PartialShareBlock(share, a, aggregatedCipherText)));
        }

        BigInteger prod_d = BigInteger.ONE;
        // compute the product of ciphertext just collected in PartialShares list
        // by using the Additive Homomorphism ElGamal Encryption Scheme.
        for (BigInteger b : partialShares) {
            prod_d = prod_d.multiply(b);
        }
        prod_d = prod_d.mod(p);

        BigInteger prod_d_inverse = prod_d.modInverse(p);

        // compute g^M
        BigInteger semiResult = prod_d_inverse.multiply(z).mod(p);

        BigInteger result = new BigInteger("0");
        // compute the final result for candidate 1
        while (true) {
            if (g.modPow(result, p).compareTo(semiResult) == 0) {
                break;
            }
            result = result.add(BigInteger.ONE);
        }

        this.resultCandidate1 = result;
        // the result for candidate 2 is equal to (TN - result for candidate 1)
        this.resultCandidate2 = new BigInteger(String.valueOf(this.votes.size())).subtract(this.resultCandidate1);
    }

    /**
     * This method returns PK<sub>voting</sub>
     *
     * @return the PK<sub>voting</sub>
     */
    public BigInteger getVotingKey() {
        return votingKey;
    }

    /**
     * This method returns voti<sub>&#937;<sub>1</sub></sub>
     *
     * @return voti<sub>&#937;<sub>1</sub></sub>
     */
    public BigInteger getResultCandidate1() {
        return resultCandidate1;
    }

    /**
     * This method returns voti<sub>&#937;<sub>2</sub></sub>
     *
     * @return voti<sub>&#937;<sub>2</sub></sub>
     */
    public BigInteger getResultCandidate2() {
        return resultCandidate2;
    }

    /**
     * This method prints all information of the smart contract.
     * 
     * @return a <code>String</code> containing all information of the smart contract.
     */
    @Override
    public String toString() {
        return "SmartContract:\nVoting Key=" + votingKey + "\nStart Election= " + startElection + "\tEnd Election= " + endElection + "\nCandidate #1 = Omega1 [v=1]\nCandidate #2 = Omega2 [v=0]";
    }

}
