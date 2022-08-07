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
 * This class represents a Verifier of the System.
 *
 * @author gparrella
 */
public abstract class Verifier {

    /**
     * This method generates and returns the <code>c</code> value, representing
     * some randomness.
     *
     * @param param the parameters that defines a cyclic group of order q.
     * @return a <code>BigInteger</code> representing the <code>c</code> value.
     */
    public BigInteger getC(CyclicGroupParameters param) {
        return new BigInteger(param.getSecurityParameter().intValue(), new SecureRandom()).mod(param.getQ());
    }

    /**
     * This method verifies the proof. it checks if a*y<sup>c</sup> mod p is
     * equal to g<sup>z</sup> mod p
     *
     * @param p the Proover
     * @param v the Verifier
     * @param param the parameters that defines a cyclic group of order q.
     * @return a <code>boolean</code> that is <code>true</code> if
     * a*y<sup>c</sup> mod p is equal to g<sup>z</sup> mod p, <code>false</code>
     * otherwise.
     */
    public boolean verifyProof(Prover p, Verifier v, CyclicGroupParameters param) {
        BigInteger a = p.getA();
        BigInteger c = v.getC(param);
        BigInteger z = p.getZ(c);

        BigInteger left = a.multiply(p.getY().modPow(c, param.getP())).mod(param.getP());
        BigInteger right = param.getG().modPow(z, param.getP());
        return left.equals(right);
    }
}
