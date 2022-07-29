package src;

import java.io.Serializable;
import src.BlockChainPackage.BlockChain;
import src.AuthorityPackage.AuthorityManagement;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import src.AuthorityPackage.Authority;
import src.BlockChainPackage.AuthorityBlock;
import src.BlockChainPackage.Block;
import src.BlockChainPackage.VoteTransaction;
import src.ElGamalHomomorphic.CyclicGroupParameters;
import src.ElGamalHomomorphic.ElGamalCipherText;
import src.ElGamalHomomorphic.ExponentialElGamal;
import src.SchnorrNIZKP.SchnorrNIProof;
import src.SchnorrNIZKP.SchnorrNIZKP;
import src.VoterPackage.Vote;
import src.VoterPackage.VoteProof;
import src.VoterPackage.Voter;

public class SmartContract implements Serializable {

    private BlockChain blockchain;
    private BigInteger votingKey;
    private LocalDateTime startElection;
    private LocalDateTime endElection;
    private AuthorityManagement manager;
    private List<ElGamalCipherText> votes;
    private BigInteger resultCandidate1 = null;
    private BigInteger resultCandidate2 = null;

    public SmartContract(LocalDateTime startElection, LocalDateTime endElection, AuthorityManagement am, String bcFilename) {
        this.blockchain = new BlockChain(bcFilename, "blockchain_serial");
        this.startElection = startElection;
        this.endElection = endElection;
        this.manager = am;
        this.votes = new ArrayList<ElGamalCipherText>();
    }

    public void blockChainInit() {
        for (Authority a : manager.getAuthorityList()) {
            SchnorrNIProof proof = SchnorrNIZKP.makeProof(a.getPrivateEncKey(), a.getPublicEncKey(), new CyclicGroupParameters());
            AuthorityBlock data = new AuthorityBlock(a.getName(), a.getPublicEncKey(), a.getPublicSigKey(), a.getCertificate(), proof);
//            System.out.println(data);
            blockchain.addBlock(new Block<AuthorityBlock>(data));
        }
    }

    public void keyGeneration() {
        this.manager.generateVotingKey();
        this.votingKey = this.manager.getVotingKey();
        blockchain.addBlock(new Block<SmartContract>(this));
    }

    public void vote(Voter voter, Vote vote, VoteProof vp, byte[] signVote) {
        if (LocalDateTime.now().isBefore(endElection) && LocalDateTime.now().isAfter(startElection)) {
            if (this.manager.validateVote(voter, vote, vp, signVote)) {
                this.votes.add(vote.getEncVote());
                VoteTransaction vt = new VoteTransaction(vote, vp, signVote);
                this.blockchain.addBlock(new Block<VoteTransaction>(vt));
                voter.setVoted();
            } else {
                throw new RuntimeException("Not valid vote");
            }
        } else {
            throw new RuntimeException("TimeOut for vote");
        }
    }

    public ElGamalCipherText aggregateCipherText() {
        ElGamalCipherText result = this.votes.get(0);
        for (int i = 1; i < this.votes.size(); i++) {
            result = ExponentialElGamal.aggregate(new CyclicGroupParameters(), result, this.votes.get(i));
        }

        this.blockchain.addBlock(new Block<ElGamalCipherText>(result));
        return result;
    }

    public void tallying(ElGamalCipherText aggregatedCipherText) {
        this.resultCandidate1 = this.manager.votesDecryption(aggregatedCipherText);
        this.resultCandidate2 = new BigInteger(String.valueOf(this.votes.size())).subtract(this.resultCandidate1);
    }

    public BigInteger getVotingKey() {
        return votingKey;
    }

    public BigInteger getResultCandidate1() {
        return resultCandidate1;
    }

    public BigInteger getResultCandidate2() {
        return resultCandidate2;
    }

    @Override
    public String toString() {
        return "SmartContract( votingKey=" + votingKey + ", startElection=" + startElection + ", endElection=" + endElection + " Candidate1 = Omega1 [v=1], Candidate2 = Omega2 [v=0] )";
    }

}
