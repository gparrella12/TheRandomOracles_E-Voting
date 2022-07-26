package src;

public class Voter {

    private Object publicKey;

    private Object privateKey;

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
