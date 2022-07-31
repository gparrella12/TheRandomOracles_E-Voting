package src;

import src.BlockChainPackage.SmartContract;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;
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
     * @param args the command voterName arguments
     */
    public static void main(String[] args) {
        // Initialization Phase
        CyclicGroupParameters param = new CyclicGroupParameters();
        AuthorityManagement am = AuthorityManagement.getInstance();
        SmartContract sc = new SmartContract(LocalDateTime.now(), LocalDateTime.now().plusDays(2), am, "blockchain.txt");
        // Blockchain initialization
        sc.blockChainInit();
        System.out.println("======== BLOCKCHAIN INITIALIZATION OK ========");
        // Key generation phase
        System.out.println("======== KEY GENERATION PHASE ========\nGenerate PK_voting...");
        sc.keyGeneration();
        System.out.println("======== KEY GENERATION OK ========");
        // Voting Phase
        Voter voter = null;
        int c1RealVotes = 0;
        System.out.println("======== START VOTING PHASE ========");
        try ( Scanner in = new Scanner(new BufferedReader(new FileReader("Certificati/Voters/.voters_list.txt")))) {
            while (in.hasNext()) {
                String voterName = in.next();
                String filename = "Certificati/Voters/" + voterName;
                voter = new Voter(filename + ".crt", filename + ".p8");
                System.out.println("\n----\nVoter: " + voter.getName() + "\tvoted = " + voter.hasVoted());
                int preference = new Random(new SecureRandom().nextLong()).nextInt(2);
                c1RealVotes += preference;
                System.out.println("Voting...");
                Vote vote = voter.makeVote(new BigInteger(String.valueOf(preference)), sc.getVotingKey(), param);
                VoteProof vp = voter.makeProof(vote);
                byte[] sign = voter.signVote(vote, vp);

                sc.vote(voter, vote, vp, sign);
                System.out.println("Valid vote generated, voted = " + voter.hasVoted());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("======== END VOTING PHASE ========");
        // Post-Voting Phase
        System.out.println("Ciphertext aggregation...");
        ElGamalCipherText resulEnc = sc.aggregateCipherText();
        System.out.println("Start tallying...");
        sc.tallying(resulEnc);
        if (!sc.getResultCandidate1().equals(new BigInteger(String.valueOf(c1RealVotes)))) {
            throw new RuntimeException("ERROR - INVALID ELECTION RESULT");
        }
        System.out.println("\n======== RESULT ========");
        // Printing the results
        System.out.println("Votes for candidate Omega1 =\t" + sc.getResultCandidate1());
        System.out.println("Votes for candidate Omega2 =\t" + sc.getResultCandidate2());
    }

}
