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
package src.BlockChainPackage;

import java.io.Serializable;
import java.math.BigInteger;
import src.AuthorityPackage.Authority;
import src.AuthorityPackage.AuthorityShareProof;
import src.CryptographicTools.ElGamalHomomorphic.ElGamalCipherText;
import src.CryptographicTools.SignatureScheme;
import src.Utils.Utils;

/**
 * This Class represents the Block that will contain the partial share of each
 * Authority, with their relative decryption ZKP, which proves that to decrypt
 * they have used the secret key associated with the public key previously
 * published on the BlockChain.
 *
 * @author gparrella
 */
public class PartialShareBlock implements Serializable {

    private final BigInteger partialShare;
    private final String authorityName;
    private final AuthorityShareProof proof;
    private final byte[] blockSignature;

    /**
     * Creates the <code>PartialShareBlock</code>, by passing:
     *
     * <ul>
     * <li><code> the partial decryption share of the authority</code></li>
     * <li><code> the authority</code></li>
     * </ul>
     *
     * @param partialShare the partial decryption share of the authority.
     * @param a the authority
     * @param c the initial ElGamal ciphertext.
     */
    public PartialShareBlock(BigInteger partialShare, Authority a, ElGamalCipherText c) {
        this.partialShare = partialShare;
        this.authorityName = a.getName();
        this.proof = new AuthorityShareProof(a, c, partialShare);
        this.blockSignature = SignatureScheme.signMessage(a.getPrivateSigKey(), partialShare.toString().concat(this.authorityName).concat(this.proof.toString()).getBytes());
    }

    /**
     * This method returns the partial share.
     *
     * @return a <code>BigInteger</code> representing the partial share.
     */
    public BigInteger getPartialShare() {
        return partialShare;
    }

    /**
     * This method returns the Authority name.
     *
     * @return a <code>String</code> representing the Authority name.
     */
    public String getAuthorityName() {
        return authorityName;
    }

    /**
     * This method prints information of the partial share.
     *
     * @return a <code>String</code> containing information of the partial
     * share.
     */
    @Override
    public String toString() {
        return "Partial Share= " + partialShare + "\nAuthority Name= " + authorityName + "\nproof= " + proof + "\nSign= " + Utils.toHex(blockSignature);
    }

}
