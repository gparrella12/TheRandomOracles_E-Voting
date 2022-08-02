/*
 * Copyright (C) 2022
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package AuthorityPackage;

import CryptographicTools.CryptographicHash;
import CryptographicTools.ElGamalHomomorphic.ElGamalCipherText;
import Utils.Utils;
import java.io.Serializable;
import java.math.BigInteger;
import org.apache.commons.lang3.SerializationUtils;



/**
 * This class contains a proof for a decryption share of an autority a. The
 * decryption share is relative to the aggregate ciphertext, to decrypt at the
 * end of the election.
 *
 * @author gparrella
 */
public class AuthorityShareProof implements Serializable {

    private final String proof;

    /**
     * This method creates a proof for an authority a and ciphertext c. In our
     * case, we simulate the proof with <code>H(a || c || share)</code>, with H
     * random oracle.
     *
     * @param a the authority
     * @param c the aggregate ciphertext
     * @param share the share of the authority
     */
    public AuthorityShareProof(Authority a, ElGamalCipherText c, BigInteger share) {
        // b = a || c || share 
        byte[] b = Utils.append(SerializationUtils.serialize(a), SerializationUtils.serialize(c), share.toByteArray());
        this.proof = Utils.toHex(CryptographicHash.hash(b));
    }

    /**
     * This method returns a string representation of the proof.
     *
     * @return a <code>String</code> representation of the proof.
     */
    @Override
    public String toString() {
        return proof;
    }

}
