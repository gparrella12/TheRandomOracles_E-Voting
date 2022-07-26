package VoterPackage;

import CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import CryptographicTools.ElGamalHomomorphic.ElGamalCipherText;
import CryptographicTools.ElGamalHomomorphic.ExponentialElGamal;
import CryptographicTools.Schnorr.ZKP.Prover;
import CryptographicTools.Schnorr.ZKP.SchnorrKeyPair;
import CryptographicTools.SignatureScheme;
import Utils.EasyLoadFromFile;
import Utils.Utils;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.apache.commons.lang3.SerializationUtils;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

/**
 * This class represent a voter with all his/her information, such as encryption
 * and signatures keys, the name, and the fact that he/she has or not voted.
 *
 *
 * @author fsonnessa
 */
public class Voter extends Prover implements Serializable {

    private String name;
    private final PublicKey publicSigKey;
    private final PrivateKey privateSigKey;

    /**
     * Creates a voter by passing:
     *
     * <ul>
     * <li><code>certificateFileName, the name of the file from which
     * the voter's certificate will be imported</code></li>
     *
     * <li><code>privateSigKey, the name of the file from which
     * the voter's private signature key will be imported</code></li>
     * </ul>
     *
     * Note that as voter's name, the value CN in certificate is used.
     *
     * @param certificateFileName name of the file containing the certificate
     * @param privSigKeyFileName name of the file containing the private
     * signature key
     */
    public Voter(String certificateFileName, String privSigKeyFileName) {
        super(new SchnorrKeyPair(CyclicGroupParameters.getInstance()));
        X509Certificate certificate = EasyLoadFromFile.loadCrt(certificateFileName);

        X500Name x500name = null;
        try {
            x500name = new JcaX509CertificateHolder(certificate).getSubject();
            RDN cn = x500name.getRDNs(BCStyle.CN)[0];
            this.name = IETFUtils.valueToString(cn.getFirst().getValue());
        } catch (CertificateEncodingException ex) {
            ex.printStackTrace();
        }

        this.publicSigKey = certificate.getPublicKey();
        this.privateSigKey = EasyLoadFromFile.loadPrivSigKey(privSigKeyFileName);
    }

    /**
     * This method creates a vote object using the voter's preference, the
     * PK<sub>voting</sub> and the parameters of a cyclic group of order q.
     *
     * Note that the exponential ElGamal encryption scheme is used
     *
     * @param preference the voter's preference, either for candidate 1 or for
     * candidate 2
     * @param votingKey the public key PK<sub>voting</sub> used for encrypt the
     * vote
     * @param param the parameters of a cyclic group of order q
     * @return a <code>Vote</code> object
     */
    public Vote makeVote(BigInteger preference, BigInteger votingKey, CyclicGroupParameters param) {
        ElGamalCipherText ciphertext = ExponentialElGamal.encrypt(param, votingKey, preference);
        return new Vote(ciphertext);
    }

    /**
     * This method creates a proof for a vote. This proof shows that the vote is
     * g^0 or g^1
     *
     * @param v the vote for which to create the proof
     * @return <code>VoteProof</code> the proof of the validity of the vote
     */
    public VoteProof makeProof(Vote v) {
        return new VoteProof(v);
    }

    /**
     * This method signs (vote || vote's proof) using the private signature key
     * of the vote's voter
     *
     * @param v the vote
     * @param vp the vote's proof
     * @return <code>SIG(Vote||VoteProof)</code>
     */
    public byte[] signVote(Vote v, VoteProof vp) {
        return SignatureScheme.signMessage(this.privateSigKey, Utils.append(SerializationUtils.serialize(v), SerializationUtils.serialize(vp)));
    }

    /**
     * This method returns the name of the voter
     *
     * @return a <code>String</code> containing the name of the voter
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the public signature key of the voter
     *
     * @return a <code>PublicKey</code> object containing the public signature
     * key of the voter.
     */
    public PublicKey getPublicSigKey() {
        return publicSigKey;
    }

    /**
     * This method returns the private signature key of the voter
     *
     * @return a <code>PrivateKey</code> object containing the private signature
     * key of the voter
     */
    public PrivateKey getPrivateSigKey() {
        return privateSigKey;
    }

    /**
     * This method prints information of the voter.
     *
     * @return a <code>String</code> containing the voter's information
     */
    @Override
    public String toString() {
        return "\nname: " + name + "\npk: " + publicSigKey.toString() + "\nsk: " + privateSigKey.toString() + "\n";
    }
}
