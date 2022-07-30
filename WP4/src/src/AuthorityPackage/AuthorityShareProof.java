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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import static src.SchnorrNIZKP.SchnorrNIZKP.getHashFunction;
import src.Utils;

/**
 *
 * @author gparrella
 */
public class AuthorityShareProof implements Serializable{
    private String proof;

    public AuthorityShareProof(Authority a) {
                MessageDigest h = null;
        try {
            h = MessageDigest.getInstance(getHashFunction(), new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        byte digest[] = new byte[h.getDigestLength()];
        digest = h.digest(a.toString().getBytes());
        this.proof = Utils.toHex(digest);
    }

    @Override
    public String toString() {
        return  proof ;
    }
    
    
    

}
