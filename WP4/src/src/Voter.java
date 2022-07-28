package src;

import java.security.Key;

public class Voter {

    private Key publicKey;
    private Key privateKey;
    private Object voterCertificate;
    private boolean voted;

    public Voter() {
    }

    public Vote vote(Vote v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Vote makeProof(Vote v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
