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
package src.AuthorityPackage;

import java.io.Serializable;
import java.math.BigInteger;
import src.CryptographicTools.CryptographicHash;
import src.CryptographicTools.ElGamalHomomorphic.ElGamalCipherText;
import src.Utils.Utils;

/**
 * This class contains a proof for a decryption share of an autority a. The
 * decryption share is relative to the aggregate ciphertext to decrypt at the
 * end of the election.
 *
 * @author gparrella
 */
public class AuthorityShareProof implements Serializable {

    private final String proof;

    /**
     * Create a proof for an authority a and ciphertext c. In our case, we
     * simulate the proof with <code>H(a || c || share)</code>, with H random oracle.
     *
     * @param a the authority
     * @param c a ciphertext that is the aggregate ciphertext
     * @param share the share of the authority
     */
    public AuthorityShareProof(Authority a, ElGamalCipherText c, BigInteger share) {
        this.proof = Utils.toHex(CryptographicHash.hash(a.toString().concat(c.toString()).concat(share.toString()).getBytes()));
    }

    /**
     * Returns a string representation of the proof.
     * @return a string a string representation of the proof.
     */
    @Override
    public String toString() {
        return proof;
    }

}
