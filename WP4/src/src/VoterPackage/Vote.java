package src.VoterPackage;

import java.io.Serializable;
import src.ElGamalHomomorphic.ElGamalCipherText;

public class Vote implements Serializable{

    private ElGamalCipherText encVote;

    public Vote(ElGamalCipherText encVote) {
        this.encVote = encVote;
    }

    public ElGamalCipherText getEncVote() {
        return encVote;
    }

    @Override
    public String toString() {
        return "Vote{" + "encVote=" + encVote + '}';
    }

}
