package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import src.AuthorityPackage.AuthorityManagement;
import src.BlockChainPackage.BlockChain;
import src.ElGamalHomomorphic.CyclicGroupParameters;
import src.VoterPackage.Vote;
import src.VoterPackage.VoteProof;
import src.VoterPackage.Voter;

/**
 *
 * @author fsonnessa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Inizializzazione
        CyclicGroupParameters param = new CyclicGroupParameters();
        AuthorityManagement am = AuthorityManagement.getInstance();
        SmartContract sc = new SmartContract(LocalDateTime.MIN, LocalDateTime.MAX, am, "blockchain.txt");
        sc.blockChainInit();
        sc.keyGeneration();
        
        
        // Voto
        BufferedReader reader;
        String line = "";
        Voter voter = null;
        try {
            reader = new BufferedReader(new FileReader("Certificati/Voters/.voters_list.txt"));
            line = reader.readLine();
            while (line != null) {
                String filename = "Certificati/Voters/" + line;
                voter = new Voter(filename + ".crt", filename + ".p8");
                //System.out.println(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Vote vote = voter.makeVote(BigInteger.ONE, sc.getVotingKey(), param);
        VoteProof vp = voter.makeProof(vote);
        byte[] sign = voter.signVote(vote, vp);
        
        sc.vote(voter, vote, vp, sign);
        voter.setVoted();
        
    }

}
