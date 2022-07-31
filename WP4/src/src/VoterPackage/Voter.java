package src.VoterPackage;

import src.Utils.EasyLoadFromFile;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import src.CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import src.CryptographicTools.ElGamalHomomorphic.ElGamalCipherText;
import src.CryptographicTools.ElGamalHomomorphic.ExponentialElGamal;
import src.CryptographicTools.SignatureScheme;

/**
 * This class contains the keys for a signature scheme and the authentication
 * certificate of the voter.
 *
 * @author fsonnessa
 */
public class Voter implements Serializable {

    private String name;
    private final PublicKey publicSigKey;
    private final PrivateKey privateSigKey;
    private X509Certificate certificate;
    private boolean voted;

    /**
     * Defines a voter by his <code>name</code>,
     * <code>private signature key</code> and <code>certificate of
     * authentication</code>.
     *
     * The certificate must contain the public signature key of the Voter.
     *
     * @param name
     * @param privateSigKey
     * @param certificate
     */
    public Voter(String name, PrivateKey privateSigKey, X509Certificate certificate) {
        this.name = name;
        this.publicSigKey = certificate.getPublicKey();
        this.privateSigKey = privateSigKey;
        this.certificate = certificate;
        this.voted = false;
    }

    /**
     * Defines a voter by his certificate of authentication, loaded from file,
     * and his private signature key.
     *
     * As Voter's name the value CN in certificate is used.
     *
     * @param certFilename
     * @param privateKeyFilename
     */
    public Voter(String certFilename, String privateKeyFilename) {
        this.certificate = EasyLoadFromFile.loadCrt(certFilename);

        X500Name x500name = null;
        try {
            x500name = new JcaX509CertificateHolder(certificate).getSubject();
            RDN cn = x500name.getRDNs(BCStyle.CN)[0];
            this.name = IETFUtils.valueToString(cn.getFirst().getValue());
        } catch (CertificateEncodingException ex) {
            Logger.getLogger(Voter.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.publicSigKey = certificate.getPublicKey();
        this.privateSigKey = EasyLoadFromFile.loadKey(privateKeyFilename);
    }

    /**
     * Create the ElGamalCipherText of the preference expressed.
     *
     * @param preference
     * @param votingKey voting key obtained from the smart contract for El Gamal
     * encryption
     * @param param CyclicGroupParameters for El Gamal Scheme specified in smart
     * contract
     * @return
     */
    public Vote makeVote(BigInteger preference, BigInteger votingKey, CyclicGroupParameters param) {
        ElGamalCipherText ciphertext = ExponentialElGamal.encrypt(param, votingKey, preference);
        return new Vote(ciphertext);
    }

    /**
     * Creates a signature to the integrity and non-repudiation of the Vote
     *
     * @param v
     * @return <code>VoteProof = H(Vote)</code>
     */
    public VoteProof makeProof(Vote v) {
        return new VoteProof(v);
    }

    /**
     * Sign the vote and its proof with the voter's private signature key for
     * integrity proprerty
     *
     * @param v
     * @param vp
     * @return <code>Sign(Vote||VoteProof, privateSignKey)</code>
     */
    public byte[] signVote(Vote v, VoteProof vp) {
        return SignatureScheme.signMessage(this.privateSigKey, v.toString().concat(vp.toString()).getBytes());
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public PublicKey getPublicSigKey() {
        return publicSigKey;
    }

    /**
     *
     * @return
     */
    public PrivateKey getPrivateSigKey() {
        return privateSigKey;
    }

    /**
     *
     * @return
     */
    public X509Certificate getCertificate() {
        return certificate;
    }

    /**
     *
     * @return
     */
    public boolean hasVoted() {
        return voted;
    }

    /**
     * Used by the smart contract to mark that the voter has already expressed
     * his preference
     */
    public void setVoted() {
        this.voted = true;
    }

    /**
     * String rappresentation of a Voter
     *
     * @return
     */
    @Override
    public String toString() {
        return "\nname: " + name + "\npk: " + publicSigKey.toString() + "\nsk: " + privateSigKey.toString() + "\ncrt: " + certificate.toString() + "\nvoted: " + voted + "\n";
    }
}
