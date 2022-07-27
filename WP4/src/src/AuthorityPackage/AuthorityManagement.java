package src.AuthorityPackage;

import src.ElGamalHomomorphic.Generation.ElGamalParametersGeneration;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import src.Vote;
import src.Voter;

/**
 *
 * @author Ernesto
 */

public class AuthorityManagement {

    private static AuthorityManagement single_instance = null;
    private List<Authority> authorityList;
    private BigInteger votingKey;

    //metodo per implementare il singleton.
    public static AuthorityManagement getInstance() {
        if (single_instance == null) {
            single_instance = new AuthorityManagement();
        }

        return single_instance;
    }

    //inizializzo la lista inserendo le 20 regioni + il ministero
    private AuthorityManagement() {
        authorityList = new ArrayList<>();
        votingKey = null;
        for (int i = 0; i < AuthorityName.values().length; i++) {
            authorityList.add(new Authority(AuthorityName.values()[i].toString()));
        }

    }

    //se pk_voting già esiste, la ritorno subito
    //se no la genero e poi la ritorno
    public BigInteger getVotingKey() {
        return this.votingKey;
    }

    //recall: (a mod p) * (b mod p) = (a*b) mod p
    private void generateVotingKey() {        
        votingKey = BigInteger.ONE;
        for (Authority a : authorityList) {            
            votingKey = votingKey.multiply(a.getPublicEncKey());
        }

        this.votingKey =  votingKey.mod(ElGamalParametersGeneration.getP());
    }

    public void validateVote(Voter voter, Vote v) {
    }

    public void votesDecryption() {
    }
}
