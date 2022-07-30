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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import src.AuthorityPackage.Authority;
import src.AuthorityPackage.AuthorityShareProof;
import src.Utils;

/**
 * This Class represents the Block that will contain the partial share of each
 * Authority with relavite decryption ZKP with the correct key.
 *
 * @author gparrella
 */
public class PartialShareBlock implements Serializable {

    private BigInteger partialShare;
    private String authorityName;
    private AuthorityShareProof proof;
    private byte[] sign;

    /**
     * Constructor of <code>PartialShareBlock</code> class.
     */
    public PartialShareBlock(BigInteger partialShare, Authority a) {
        this.partialShare = partialShare;
        this.authorityName = a.getName();
        this.proof = new AuthorityShareProof(a);

        try {
            Signature signature = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());
            // generate a signature
            // initialize signature for sign with private key K.getPrivate() and a secure random source
            signature.initSign(a.getPrivateSigKey(), new SecureRandom());

            signature.update(partialShare.toString().concat(this.authorityName).concat(this.proof.toString()).getBytes());
            this.sign = signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Function used to get the partial share.
     *
     * @return a <code>BigInteger</code> representing the partial share.
     */
    public BigInteger getPartialShare() {
        return partialShare;
    }

    /**
     * Function used to get the Authority name.
     *
     * @return a <code>String</code> representing the Authority name.
     */
    public String getAuthorityName() {
        return authorityName;
    }

    /**
     * Function used to print the informations on the BlockChain (in our case,
     * on a file .txt)
     *
     * @return
     */
    @Override
    public String toString() {
        return "Partial Share= " + partialShare + "\nAuthority Name= " + authorityName + "\nproof= " + proof + "\nSign= " + Utils.toHex(sign);
    }

}
