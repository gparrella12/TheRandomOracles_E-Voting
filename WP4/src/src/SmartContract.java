package src;

import src.BlockChainPackage.BlockChain;
import src.AuthorityPackage.AuthorityManagement;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class SmartContract {

    private BlockChain blockchain;

    private BigInteger votingKey;

    private LocalDateTime startElection;

    private LocalDateTime endElection;

    private AuthorityManagement manager;

    public SmartContract(LocalDateTime startElection,LocalDateTime endElection) {
        this.blockchain = new BlockChain("file.txt");
        this.startElection = startElection;
        this.endElection = endElection;
        this.manager = AuthorityManagement.getInstance();
    }

    public void keyGeneration() {
        this.votingKey = this.manager.getVotingKey();
    }

    public void vote(Voter voter, Vote vote) {
        this.manager.validateVote(voter, vote);
    }

    public void tallying() {
        this.manager.votesDecryption();
    }

    public BigInteger getVotingKey() {
        return votingKey;
    }    
}
