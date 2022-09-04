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
package CryptographicTools.Schnorr.ZKP;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class represent a Prover of the System. It has to generate the
 * <code>a</code> and <code>y</code> values.
 *
 * @author gparrella
 */
public abstract class Prover {

    private BigInteger schnorrRandomness = null;
    private SchnorrKeyPair keys;

    /**
     * Starting from the <code>SchnorrKeyPair</code> keys, it generates the
     * <code>a</code> and <code>y</code> values.
     *
     * @param keys
     */
    public Prover(SchnorrKeyPair keys) {
        this.keys = keys;
    }

    /**
     * This method generates and returns the <code>a</code> value, that is equal
     * to g<sup>r</sup> mod p.
     *
     * @return a <code>BigInteger</code> representing the <code>a</code> value.
     */
    public BigInteger getA() {
        schnorrRandomness = new BigInteger(keys.getParam().getSecurityParameter().intValue(), new SecureRandom()).mod(keys.getParam().getQ());
        return keys.getParam().getG().modPow(schnorrRandomness, keys.getParam().getP()); // a = g^r mod p
    }

    /**
     * This method generates and returns the <code>a</code> value, that is equal
     * to r+c*x.
     *
     * @param c is the random challenge from the verifier.
     * @return a <code>BigInteger</code> representing the <code>z</code> value.
     */
    public BigInteger getZ(BigInteger c) {
        return schnorrRandomness.add(c.multiply(keys.getX())).mod(keys.getParam().getQ()); // z = r + x*c mod q
    }

    /**
     * This method returns the <code>y</code> value, that is equal to
     * g<sup>x</sup> mod p.
     *
     * @return a <code>BigInteger</code> representing the <code>y</code> value.
     */
    public BigInteger getY() {
        return keys.getY();
    }
}
