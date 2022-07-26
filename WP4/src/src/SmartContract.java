package src;

import java.security.Key;
import java.time.LocalDateTime;

public class SmartContract {

    private BlockChain blockchain;

    private Key votingKey;

    private LocalDateTime startElection;

    private LocalDateTime endElection;

    private AuthorityManagement manager;

    public SmartContract(LocalDateTime startElection,LocalDateTime endElection) {
        this.blockchain = new BlockChain("file.txt");
        this.startElection = startElection;
        this.endElection = endElection;
        this.manager = new AuthorityManagement();
    }

    public void keyGeneration() {
        this.votingKey = this.manager.generateVotingKey();
    }

    public void vote(Voter voter, Vote vote) {
        this.manager.validateVote(voter, vote);
    }

    public void tallying() {
        this.manager.votesDecryption();
    }
}
