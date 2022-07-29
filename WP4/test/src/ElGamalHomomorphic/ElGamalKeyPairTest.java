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
package src.ElGamalHomomorphic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gparrella
 */
public class ElGamalKeyPairTest {

    ElGamalKeyPair pair = new ElGamalKeyPair();

    public ElGamalKeyPairTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPublicKey method, of class ElGamalKeyPair.
     */
    @Test
    public void testGetPublicKey() {
        System.out.println("getPublicKey");
        System.out.println(pair.getPublicKey());
        CyclicGroupParameters param = new CyclicGroupParameters();
        if (!pair.getPublicKey().equals(param.getG().modPow(pair.getSecretKey().mod(param.getQ()), param.getP()))) {
            fail("error");
        } else {
            System.out.println("Public/Private key Well Formed");
        }
    }

    /**
     * Test of getSecretKey method, of class ElGamalKeyPair.
     */
    @Test
    public void testGetSecretKey() {
        System.out.println("getSecretKey");
        System.out.println(pair.getSecretKey());

    }

}
