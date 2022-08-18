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

import CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
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
        CyclicGroupParameters param = keys.getParam();
        int securityParameter = param.getSecurityParameter().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        // Take a random value of securityParameter bit.
        // h <- Z_p*
        BigInteger h = new BigInteger(securityParameter, new SecureRandom()).mod(p);
        while (h.equals(BigInteger.ONE)) {
            h = new BigInteger(securityParameter, new SecureRandom()).mod(p);
        }
        // SK = h^2 mod p (because p=2q+1), this is a group element of cyclic group of order q [pag. 322]
        this.schnorrRandomness = h.modPow(new BigInteger("2"), p);
        if (isInQSubgroup(this.schnorrRandomness, p) == 0) {
            throw new RuntimeException("Malformed Schnorr randomness");
        }
        // return a = g^r mod p 
        return g.modPow(schnorrRandomness, p);
    }

    private static int isInQSubgroup(BigInteger x, BigInteger p) {
        // x ^ {(p-1)/2} mod p == 1 <-> x^q = 1 mod p [pag. 323]
        if (x.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.TWO), p).compareTo(BigInteger.ONE) == 0) {
            return 1;
        }
        return 0;
    }

    /**
     * This method generates and returns the <code>a</code> value, that is equal
     * to r+c*x.
     *
     * @param c is the challenge from the verifier.
     * @return a <code>BigInteger</code> representing the <code>z</code> value.
     */
    public BigInteger getZ(BigInteger c) {
        return schnorrRandomness.add(c.multiply(keys.getX())).mod(keys.getParam().getQ());
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
