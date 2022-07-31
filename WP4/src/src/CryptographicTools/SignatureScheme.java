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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class contains some static methods to sign a message with a defined
 * signature scheme. The signature scheme is specified in the
 * <code>algorithm</code> parameter.
 *
 * @author gparrella
 */
public class SignatureScheme {

    /**
     * The string representation of the signature algorithm used. Default
     * algorithm: SHA256withECDSA.
     */
    private static String algorithm = "SHA256withECDSA";

    /**
     * Sign a message m.
     *
     * @param key the private key used to sign the message.
     * @param message the message to sign.
     * @return the signature of the message.
     */
    public static byte[] signMessage(PrivateKey key, byte[] message) {
        try {
            Signature signature = Signature.getInstance(algorithm, new BouncyCastleProvider());
            // generate a signature
            signature.initSign(key, new SecureRandom());

            signature.update(message);
            return signature.sign();

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean verifySignature(PublicKey publicKey, byte[] message, byte[] messageSign) {
        try {
            Signature signature = Signature.getInstance(algorithm, new BouncyCastleProvider());
            signature.initVerify(publicKey);
            signature.update(message);
            return signature.verify(messageSign);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Get the signature algorithm used.
     *
     * @return
     */
    public static String getAlgorithm() {
        return algorithm;
    }

    /**
     * Set the signature algorithm.
     *
     * @param a the new signature algoritm
     */
    public static void setAlgorithm(String a) {
        algorithm = a;
    }
}
