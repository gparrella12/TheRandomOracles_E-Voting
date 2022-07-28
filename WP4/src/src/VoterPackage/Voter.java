package src.VoterPackage;

import java.math.BigInteger;
import java.security.Key;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import src.ElGamalHomomorphic.CyclicGroupParameters;
import src.ElGamalHomomorphic.ElGamalCipherText;
import src.ElGamalHomomorphic.ExponentialElGamal;

/**
 *
 * @author fsonnessa
 */
public class Voter {

    private String name;
    private Key publicKey;  // le chiavi del votante servono per schemi di firma
    private Key privateKey;
    private X509Certificate certificate;
    private boolean voted;
    
    public Voter(String name, Key privateKey, X509Certificate certificate) {
        this.name = name;
        this.publicKey = certificate.getPublicKey();
        this.privateKey = privateKey;
        this.certificate = certificate;
    }

    public Voter(String certFilename, String privateKeyFilename) {        
        this.certificate = Loader.loadCrtFromFile(certFilename);

        X500Name x500name = null;
        try {
            x500name = new JcaX509CertificateHolder(certificate).getSubject();
            RDN cn = x500name.getRDNs(BCStyle.CN)[0];
            this.name = IETFUtils.valueToString(cn.getFirst().getValue());
        } catch (CertificateEncodingException ex) {
            Logger.getLogger(Voter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.publicKey = certificate.getPublicKey();
        this.privateKey = Loader.loadSkFromFile(privateKeyFilename);
    }

    public Vote vote(BigInteger preference, BigInteger votingKey, CyclicGroupParameters param) {        
        ElGamalCipherText ciphertext = ExponentialElGamal.encrypt(param, votingKey, preference);
        return new Vote(ciphertext, certificate, true);
    }

    public Vote makeProof(Vote v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        return name;
    }

    public Key getPublicKey() {
        return publicKey;
    }

    public Key getPrivateKey() {
        return privateKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public boolean isVoted() {
        return voted;
    }

    @Override
    public String toString() {
        return "\n" + "name: " + name + "\npk: " + publicKey.getEncoded() + "\nsk: " + privateKey.getEncoded() + "\ncrt: " + certificate.getType() + "\nvoted: " + voted + "\n";
    }
}
