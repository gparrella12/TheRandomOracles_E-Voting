package src.Voter;

import java.security.cert.X509Certificate;
import src.ElGamalHomomorphic.ElGamalCipherText;

public class Vote {

    private ElGamalCipherText encVote;
    private X509Certificate voterCertificate;
    private boolean voteProof;

    public Vote(ElGamalCipherText encVote, X509Certificate voterCertificate, boolean voteProof) {
        this.encVote = encVote;
        this.voterCertificate = voterCertificate;
        this.voteProof = voteProof;
    }

    public ElGamalCipherText getEncVote() {
        return encVote;
    }

    public X509Certificate getVoterCertificate() {
        return voterCertificate;
    }

    public boolean isVoteProof() {
        return voteProof;
    }

    
}
