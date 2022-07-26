package src;

import java.security.Key;

public class Authority {

    private String name;

    private Key privateEncKey;

    private Key publicEncKey;

    private Key privateSigKey;

    private Key publicSigKey;

    private Object authorityCertificate;

    public Authority() {
    }
}
