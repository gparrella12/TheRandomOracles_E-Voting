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
package src.CryptographicTools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class represents SHA256
 *
 * @author gparrella
 */
public class CryptographicHash {
        
    private static String hashFunction = "SHA256";

    /**
     * This method returns the hash of the input
     * 
     * @param input the array of bytes on which compute the hash
     * @return SHA256(input)
     */
    public static byte[] hash(byte[] input) {
        MessageDigest h = null;
        try {
            h = MessageDigest.getInstance(getHashFunction(), new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        byte digest[] = new byte[h.getDigestLength()];
        return h.digest(input);

    }

    /**
     * This method returns the used hash function
     *
     * @return the hash function.
     */
    public static String getHashFunction() {
        return hashFunction;
    }

    /**
     * This method sets the type of hash function to use
     *  
     * @param hashFunction the new hash function
     */
    public static void setHashFunction(String hashFunction) {
        CryptographicHash.hashFunction = hashFunction;
    }
}
