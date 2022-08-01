package CryptographicTools.ElGamalHomomorphic;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * This class contains a ciphertext of ElGamal. 
 * In particular, this class contains a ciphertext <code> (u,v) </code> with:
 * <ul>
 * <li><code>u = g<sup>r</sup> mod p</code> , 
 * with <code>r</code> randomly chosen in <code>Z_q</code></li>
 * 
 * <li><code>v = g<sup>m</sup> * y<sup>r</sup></code>, 
 * where <code>m</code> is the message to send, 
 * and <code>y</code> is the public key.
 * </ul>
 *
 * @author gparrella
 */
public class ElGamalCipherText implements Serializable {
    // u = g^r, with r randomly chosen in Z_q
    private final BigInteger u; 
    
    // v = g^m * y^r, with y public key
    private final BigInteger v; 

    /**
     * Create an ElGamal ciphertext given u and v.
     * @param u <code>u = g<sup>r</sup> mod p</code>, 
     * with <code>r</code> randomly chosen in <code>Z_q</code>
     *     
     * @param v <code>v = g<sup>m</sup> * y<sup>r</sup></code>,
     * where <code>m</code> is the message to send, 
     * and <code>y</code> the public key.
     */
    public ElGamalCipherText(BigInteger u, BigInteger v) {
        this.u = u;
        this.v = v;
    }

    /**
     * This method returns the u value of the ciphertext (u,v)
     * @return a BigInteger with <code>u</code> value.
     */
    public BigInteger getU() {
        return u;
    }

    /**
     * This method returns the v value of the ciphertext (u,v)
     * @return a BigInteger with <code>v</code> value.
     */
    public BigInteger getV() {
        return v;
    }

    /**
     * This method returns a string representation of the ciphertext (u,v)
     * @return a string that represents of the ciphertext (u,v)
     */
    @Override
    public String toString() {
        return "(" + "u=" + u + ", v=" + v + ')';
    }

}
