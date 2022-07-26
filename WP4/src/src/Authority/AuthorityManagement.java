package src.Authority;

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
        for (int i = 0; i < AutorithyName.AutorithyName.length; i++) {
            authorityList.add(new Authority(AutorithyName.AutorithyName[i]));
        }

    }

    //se pk_voting già esiste, la ritorno subito
    //se no la genero e poi la ritorno
    public BigInteger getVotingKey() {
        if (votingKey != null) {
            return votingKey;
        }

        return generateVotingKey();
    }

    //recall: (a mod p) * (b mod p) = (a*b) mod p
    private BigInteger generateVotingKey() {        
        votingKey = BigInteger.ONE;
        for (Authority a : authorityList) {            
            votingKey = votingKey.multiply(a.getPublicEncKey());
        }

        return votingKey.mod(ElGamalPair.getInstance().getP());
    }

    public void validateVote(Voter voter, Vote v) {
    }

    public void votesDecryption() {
    }
}
