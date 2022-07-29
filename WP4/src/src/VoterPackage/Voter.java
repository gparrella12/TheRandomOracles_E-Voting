package src.VoterPackage;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import src.ElGamalHomomorphic.CyclicGroupParameters;
import src.ElGamalHomomorphic.ElGamalCipherText;
import src.ElGamalHomomorphic.ExponentialElGamal;

/**
 *
 * @author fsonnessa
 */
public class Voter implements Serializable{

    private String name;
    private final PublicKey publicSigKey;
    private final PrivateKey privateSigKey;
    private X509Certificate certificate;
    private boolean voted;

    public Voter(String name, PrivateKey privateSigKey, X509Certificate certificate) {
        this.name = name;
        this.publicSigKey = certificate.getPublicKey();
        this.privateSigKey = privateSigKey;
        this.certificate = certificate;
        this.voted = false;
    }

    public Voter(String certFilename, String privateKeyFilename) {
        this.certificate = CertificateLoader.loadCrtFromFile(certFilename);

        X500Name x500name = null;
        try {
            x500name = new JcaX509CertificateHolder(certificate).getSubject();
            RDN cn = x500name.getRDNs(BCStyle.CN)[0];
            this.name = IETFUtils.valueToString(cn.getFirst().getValue());
        } catch (CertificateEncodingException ex) {
            Logger.getLogger(Voter.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.publicSigKey = certificate.getPublicKey();
        this.privateSigKey = CertificateLoader.loadSkFromFile(privateKeyFilename);
    }

    public Vote makeVote(BigInteger preference, BigInteger votingKey, CyclicGroupParameters param) {
        ElGamalCipherText ciphertext = ExponentialElGamal.encrypt(param, votingKey, preference);
        return new Vote(ciphertext);
    }

    public VoteProof makeProof(Vote v) {
        return new VoteProof(v);
    }
    
    public byte[] signVote(Vote v, VoteProof vp){
        try {
            Signature signature = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());
            // generate a signature
            signature.initSign(this.privateSigKey, new SecureRandom()); // initialize signature for sign with private key K.getPrivate() and a secure random source
            
            signature.update(v.toString().concat(vp.toString()).getBytes());
            return signature.sign();
            
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public PublicKey getPublicSigKey() {
        return publicSigKey;
    }

    public PrivateKey getPrivateSigKey() {
        return privateSigKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public boolean hasVoted() {
        return voted;
    }

    public void setVoted() {
        this.voted = true;
    }

    @Override
    public String toString() {
        return "\n" + "name: " + name + "\npk: " + publicSigKey.toString() + "\nsk: " + privateSigKey.toString() + "\ncrt: " + certificate.toString() + "\nvoted: " + voted + "\n";
    }
}
