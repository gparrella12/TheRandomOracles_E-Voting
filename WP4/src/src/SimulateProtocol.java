package src;

import src.BlockChainPackage.SmartContract;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import src.AuthorityPackage.AuthorityManagement;
import src.CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import src.CryptographicTools.ElGamalHomomorphic.ElGamalCipherText;
import src.VoterPackage.Vote;
import src.VoterPackage.VoteProof;
import src.VoterPackage.Voter;

/**
 *
 * @author fsonnessa
 */
public class SimulateProtocol {

    /**
     * Here we are simulating the entire protocol.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Initialization Phase
        CyclicGroupParameters param = new CyclicGroupParameters();
        AuthorityManagement am = AuthorityManagement.getInstance();
        SmartContract sc = new SmartContract(LocalDateTime.MIN, LocalDateTime.MAX, am, "blockchain.txt");
        sc.blockChainInit();
        sc.keyGeneration();

        // Voting Phase
        BufferedReader reader;
        String line = "";
        Voter voter = null;
        try {
            reader = new BufferedReader(new FileReader("Certificati/Voters/.voters_list.txt"));
            line = reader.readLine();
            while (line != null) {
                String filename = "Certificati/Voters/" + line;
                voter = new Voter(filename + ".crt", filename + ".p8");

                int preference = new Random(new SecureRandom().nextLong()).nextInt(2);

                // Printing all votes
                System.out.println(voter.getName() + " " + preference);

                Vote vote = voter.makeVote(new BigInteger(String.valueOf(preference)), sc.getVotingKey(), param);
                VoteProof vp = voter.makeProof(vote);
                byte[] sign = voter.signVote(vote, vp);

                sc.vote(voter, vote, vp, sign);

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Post-Voting Phase
        ElGamalCipherText resulEnc = sc.aggregateCipherText();
        sc.tallying(resulEnc);

        // Printing the results
        System.out.println("C1: " + sc.getResultCandidate1() + " C2: " + sc.getResultCandidate2());

    }

}
