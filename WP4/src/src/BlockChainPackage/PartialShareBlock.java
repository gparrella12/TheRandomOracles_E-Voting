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
 *
 * @author gparrella
 */
public class PartialShareBlock implements Serializable {

    private BigInteger partialShare;
    private String authorityName;
    private AuthorityShareProof proof;
    private byte[] sign;

    public PartialShareBlock(BigInteger partialShare, Authority a) {
        this.partialShare = partialShare;
        this.authorityName = a.getName();
        this.proof = new AuthorityShareProof(a);

        try {
            Signature signature = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());
            // generate a signature
            signature.initSign(a.getPrivateSigKey(), new SecureRandom()); // initialize signature for sign with private key K.getPrivate() and a secure random source

            signature.update(partialShare.toString().concat(this.authorityName).concat(this.proof.toString()).getBytes());
            this.sign = signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            ex.printStackTrace();
        }
    }

    public BigInteger getPartialShare() {
        return partialShare;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    @Override
    public String toString() {
        return "PartialShare:\npartialShare=" + partialShare + "\nauthorityName=" + authorityName + "\nproof=" + proof + "\nSign="+Utils.toHex(sign);
    }

}
