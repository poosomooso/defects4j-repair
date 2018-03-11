/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.math3.special;

import org.apache.commons.math3.TestUtils;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.util.FastMath;

import org.junit.Test;
import org.junit.Assert;

/**
 * @version $Id$
 */
public class GammaTest {
    private void testRegularizedGamma(double expected, double a, double x) {
        double actualP = Gamma.regularizedGammaP(a, x);
        double actualQ = Gamma.regularizedGammaQ(a, x);
        TestUtils.assertEquals(expected, actualP, 10e-15);
        TestUtils.assertEquals(actualP, 1.0 - actualQ, 10e-15);
    }

    private void testLogGamma(double expected, double x) {
        double actual = Gamma.logGamma(x);
        TestUtils.assertEquals(expected, actual, 10e-15);
    }

    @Test
    public void testRegularizedGammaNanPositive() {
        testRegularizedGamma(Double.NaN, Double.NaN, 1.0);
    }

    @Test
    public void testRegularizedGammaPositiveNan() {
        testRegularizedGamma(Double.NaN, 1.0, Double.NaN);
    }

    @Test
    public void testRegularizedGammaNegativePositive() {
        testRegularizedGamma(Double.NaN, -1.5, 1.0);
    }

    @Test
    public void testRegularizedGammaPositiveNegative() {
        testRegularizedGamma(Double.NaN, 1.0, -1.0);
    }

    @Test
    public void testRegularizedGammaZeroPositive() {
        testRegularizedGamma(Double.NaN, 0.0, 1.0);
    }

    @Test
    public void testRegularizedGammaPositiveZero() {
        testRegularizedGamma(0.0, 1.0, 0.0);
    }

    @Test
    public void testRegularizedGammaPositivePositive() {
        testRegularizedGamma(0.632120558828558, 1.0, 1.0);
    }

    @Test
    public void testLogGammaNan() {
        testLogGamma(Double.NaN, Double.NaN);
    }

    @Test
    public void testLogGammaNegative() {
        testLogGamma(Double.NaN, -1.0);
    }

    @Test
    public void testLogGammaZero() {
        testLogGamma(Double.NaN, 0.0);
    }

    @Test
    public void testLogGammaPositive() {
        testLogGamma(0.6931471805599457, 3.0);
    }

    @Test
    public void testDigammaLargeArgs() {
        double eps = 1e-8;
        Assert.assertEquals(4.6001618527380874002, Gamma.digamma(100), eps);
        Assert.assertEquals(3.9019896734278921970, Gamma.digamma(50), eps);
        Assert.assertEquals(2.9705239922421490509, Gamma.digamma(20), eps);
        Assert.assertEquals(2.9958363947076465821, Gamma.digamma(20.5), eps);
        Assert.assertEquals(2.2622143570941481605, Gamma.digamma(10.1), eps);
        Assert.assertEquals(2.1168588189004379233, Gamma.digamma(8.8), eps);
        Assert.assertEquals(1.8727843350984671394, Gamma.digamma(7), eps);
        Assert.assertEquals(0.42278433509846713939, Gamma.digamma(2), eps);
        Assert.assertEquals(-100.56088545786867450, Gamma.digamma(0.01), eps);
        Assert.assertEquals(-4.0390398965921882955, Gamma.digamma(-0.8), eps);
        Assert.assertEquals(4.2003210041401844726, Gamma.digamma(-6.3), eps);
    }

    @Test
    public void testDigammaSmallArgs() {
        // values for negative powers of 10 from 1 to 30 as computed by webMathematica with 20 digits
        // see functions.wolfram.com
        double[] expected = {-10.423754940411076795, -100.56088545786867450, -1000.5755719318103005,
                -10000.577051183514335, -100000.57719921568107, -1.0000005772140199687e6, -1.0000000577215500408e7,
                -1.0000000057721564845e8, -1.0000000005772156633e9, -1.0000000000577215665e10, -1.0000000000057721566e11,
                -1.0000000000005772157e12, -1.0000000000000577216e13, -1.0000000000000057722e14, -1.0000000000000005772e15, -1e+16,
                -1e+17, -1e+18, -1e+19, -1e+20, -1e+21, -1e+22, -1e+23, -1e+24, -1e+25, -1e+26,
                -1e+27, -1e+28, -1e+29, -1e+30};
        for (double n = 1; n < 30; n++) {
            checkRelativeError(String.format("Test %.0f: ", n), expected[(int) (n - 1)], Gamma.digamma(FastMath.pow(10.0, -n)), 1e-8);
        }
    }

    @Test
    public void testTrigamma() {
        double eps = 1e-8;
        // computed using webMathematica.  For example, to compute trigamma($i) = Polygamma(1, $i), use
        //
        // http://functions.wolfram.com/webMathematica/Evaluated.jsp?name=PolyGamma2&plottype=0&vars={%221%22,%22$i%22}&digits=20
        double[] data = {
                1e-4, 1.0000000164469368793e8,
                1e-3, 1.0000016425331958690e6,
                1e-2, 10001.621213528313220,
                1e-1, 101.43329915079275882,
                1, 1.6449340668482264365,
                2, 0.64493406684822643647,
                3, 0.39493406684822643647,
                4, 0.28382295573711532536,
                5, 0.22132295573711532536,
                10, 0.10516633568168574612,
                20, 0.051270822935203119832,
                50, 0.020201333226697125806,
                100, 0.010050166663333571395
        };
        for (int i = data.length - 2; i >= 0; i -= 2) {
            Assert.assertEquals(String.format("trigamma %.0f", data[i]), data[i + 1], Gamma.trigamma(data[i]), eps);
        }
    }

    /**
     * Reference data for the {@link Gamma#logGamma(double)} function. This data
     * was generated with the following <a
     * href="http://maxima.sourceforge.net/">Maxima</a> script.
     *
     * <pre>
     * kill(all);
     *
     * fpprec : 64;
     * gamln(x) := log(gamma(x));
     * x : append(makelist(bfloat(i / 8), i, 1, 80),
     *     [0.8b0, 1b2, 1b3, 1b4, 1b5, 1b6, 1b7, 1b8, 1b9, 1b10]);
     *
     * for i : 1 while i <= length(x) do
     *     print("{", float(x[i]), ",", float(gamln(x[i])), "},");
     * </pre>
     */
    private static final double[][] LOG_GAMMA_REF = {
        { 0.125 , 2.019418357553796 },
        { 0.25 , 1.288022524698077 },
        { 0.375 , .8630739822706475 },
        { 0.5 , .5723649429247001 },
        { 0.625 , .3608294954889402 },
        { 0.75 , .2032809514312954 },
        { 0.875 , .08585870722533433 },
        { 0.890625 , .07353860936979656 },
        { 0.90625 , .06169536624059108 },
        { 0.921875 , .05031670080005688 },
        { 0.9375 , 0.0393909017345823 },
        { 0.953125 , .02890678734595923 },
        { 0.96875 , .01885367233441289 },
        { 0.984375 , .009221337197578781 },
        { 1.0 , 0.0 },
        { 1.015625 , - 0.00881970970573307 },
        { 1.03125 , - .01724677500176807 },
        { 1.046875 , - .02528981394675729 },
        { 1.0625 , - .03295710029357782 },
        { 1.078125 , - .04025658272400143 },
        { 1.09375 , - .04719590272716985 },
        { 1.109375 , - .05378241123619192 },
        { 1.125 , - .06002318412603958 },
        { 1.25 , - .09827183642181316 },
        { 1.375 , - .1177552707410788 },
        { 1.5 , - .1207822376352452 },
        { 1.625 , - .1091741337567954 },
        { 1.75 , - .08440112102048555 },
        { 1.875 , - 0.0476726853991883 },
        { 1.890625 , - .04229320615532515 },
        { 1.90625 , - .03674470657266143 },
        { 1.921875 , - .03102893865389552 },
        { 1.9375 , - .02514761940298887 },
        { 1.953125 , - .01910243184040138 },
        { 1.96875 , - .01289502598016741 },
        { 1.984375 , - .006527019770560387 },
        { 2.0 , 0.0 },
        { 2.015625 , .006684476830232185 },
        { 2.03125 , .01352488366498562 },
        { 2.046875 , .02051972208453692 },
        { 2.0625 , .02766752152285702 },
        { 2.078125 , 0.0349668385135861 },
        { 2.09375 , .04241625596251728 },
        { 2.109375 , .05001438244545164 },
        { 2.125 , .05775985153034387 },
        { 2.25 , .1248717148923966 },
        { 2.375 , .2006984603774558 },
        { 2.5 , .2846828704729192 },
        { 2.625 , .3763336820249054 },
        { 2.75 , .4752146669149371 },
        { 2.875 , .5809359740231859 },
        { 2.890625 , .5946142560817441 },
        { 2.90625 , .6083932548009232 },
        { 2.921875 , .6222723333588501 },
        { 2.9375 , .6362508628423761 },
        { 2.953125 , .6503282221022278 },
        { 2.96875 , .6645037976116387 },
        { 2.984375 , 0.678776983328359 },
        { 3.0 , .6931471805599453 },
        { 3.015625 , .7076137978322324 },
        { 3.03125 , .7221762507608962 },
        { 3.046875 , .7368339619260166 },
        { 3.0625 , 0.751586360749556 },
        { 3.078125 , .7664328833756681 },
        { 3.09375 , .7813729725537568 },
        { 3.109375 , .7964060775242092 },
        { 3.125 , 0.811531653906724 },
        { 3.25 , .9358019311087253 },
        { 3.375 , 1.06569589786406 },
        { 3.5 , 1.200973602347074 },
        { 3.625 , 1.341414578068493 },
        { 3.75 , 1.486815578593417 },
        { 3.875 , 1.6369886482725 },
        { 4.0 , 1.791759469228055 },
        { 4.125 , 1.950965937095089 },
        { 4.25 , 2.114456927450371 },
        { 4.375 , 2.282091222188554 },
        { 4.5 , 2.453736570842442 },
        { 4.625 , 2.62926886637513 },
        { 4.75 , 2.808571418575736 },
        { 4.875 , 2.99153431107781 },
        { 5.0 , 3.178053830347946 },
        { 5.125 , 3.368031956881733 },
        { 5.25 , 3.561375910386697 },
        { 5.375 , 3.757997741998131 },
        { 5.5 , 3.957813967618717 },
        { 5.625 , 4.160745237339519 },
        { 5.75 , 4.366716036622286 },
        { 5.875 , 4.57565441552762 },
        { 6.0 , 4.787491742782046 },
        { 6.125 , 5.002162481906205 },
        { 6.25 , 5.219603986990229 },
        { 6.375 , 5.439756316011858 },
        { 6.5 , 5.662562059857142 },
        { 6.625 , 5.887966185430003 },
        { 6.75 , 6.115915891431546 },
        { 6.875 , 6.346360475557843 },
        { 7.0 , 6.579251212010101 },
        { 7.125 , 6.814541238336996 },
        { 7.25 , 7.05218545073854 },
        { 7.375 , 7.292140407056348 },
        { 7.5 , 7.534364236758733 },
        { 7.625 , 7.778816557302289 },
        { 7.75 , 8.025458396315983 },
        { 7.875 , 8.274252119110479 },
        { 8.0 , 8.525161361065415 },
        { 8.125 , 8.77815096449171 },
        { 8.25 , 9.033186919605123 },
        { 8.375 , 9.290236309282232 },
        { 8.5 , 9.549267257300997 },
        { 8.625 , 9.810248879795765 },
        { 8.75 , 10.07315123968124 },
        { 8.875 , 10.33794530382217 },
        { 9.0 , 10.60460290274525 },
        { 9.125 , 10.87309669270751 },
        { 9.25 , 11.14340011995171 },
        { 9.375 , 11.41548738699336 },
        { 9.5 , 11.68933342079727 },
        { 9.625 , 11.96491384271319 },
        { 9.75 , 12.24220494005076 },
        { 9.875 , 12.52118363918365 },
        { 10.0 , 12.80182748008147 },
        { 0.8 , .1520596783998376 },
        { 100.0 , 359.1342053695754 },
        { 1000.0 , 5905.220423209181 },
        { 10000.0 , 82099.71749644238 },
        { 100000.0 , 1051287.708973657 },
        { 1000000.0 , 1.2815504569147612e+7 },
        { 10000000.0 , 1.511809493694739e+8 },
        { 1.e+8 , 1.7420680661038346e+9 },
        { 1.e+9 , 1.972326582750371e+10 },
        { 1.e+10 , 2.202585092888106e+11 },
    };

    @Test
    public void testLogGamma() {
        final int ulps = 3;
        for (int i = 0; i < LOG_GAMMA_REF.length; i++) {
            final double[] data = LOG_GAMMA_REF[i];
            final double x = data[0];
            final double expected = data[1];
            final double actual = Gamma.logGamma(x);
            final double tol;
            if (expected == 0.0) {
                tol = 1E-15;
            } else {
                tol = ulps * FastMath.ulp(expected);
            }
            Assert.assertEquals(Double.toString(x), expected, actual, tol);
        }
    }

    @Test
    public void testLogGammaPrecondition1() {
        Assert.assertTrue(Double.isNaN(Gamma.logGamma(0.0)));
    }

    @Test
    public void testLogGammaPrecondition2() {
        Assert.assertTrue(Double.isNaN(Gamma.logGamma(-1.0)));
    }

    /**
     * <p>
     * Reference values for the {@link Gamma#invGamma1pm1(double)} method.
     * These values were generated with the following <a
     * href="http://maxima.sourceforge.net/">Maxima</a> script
     * </p>
     *
     * <pre>
     * kill(all);
     *
     * fpprec : 64;
     * gam1(x) := 1 / gamma(1 + x) - 1;
     * x : makelist(bfloat(i / 8), i, -4, 12);
     *
     * for i : 1 while i <= length(x) do print("{",
     *                                         float(x[i]),
     *                                         ",",
     *                                         float(gam1(x[i])),
     *                                         "},");
     * </pre>
     */
    private static final double[][] INV_GAMMA1P_M1_REF = {
        { -0.5 , -.4358104164522437 },
        { -0.375 , -.3029021533379859 },
        { -0.25 , -0.183951060901737 },
        { -0.125 , -.08227611018520711 },
        { 0.0 , 0.0 },
        { 0.125 , .06186116458306091 },
        { 0.25 , .1032626513208373 },
        { 0.375 , .1249687649039041 },
        { 0.5 , .1283791670955126 },
        { 0.625 , .1153565546592225 },
        { 0.75 , 0.0880652521310173 },
        { 0.875 , .04882730264547758 },
        { 1.0 , 0.0 },
        { 1.125 , -.05612340925950141 },
        { 1.25 , -.1173898789433302 },
        { 1.375 , -.1818408982517061 },
        { 1.5 , -0.247747221936325 },
    };

    @Test
    public void testInvGamma1pm1() {

        final int ulps = 3;
        for (int i = 0; i < INV_GAMMA1P_M1_REF.length; i++) {
            final double[] ref = INV_GAMMA1P_M1_REF[i];
            final double x = ref[0];
            final double expected = ref[1];
            final double actual = Gamma.invGamma1pm1(x);
            final double tol = ulps * FastMath.ulp(expected);
            Assert.assertEquals(Double.toString(x), expected, actual, tol);
        }
    }

    @Test(expected = NumberIsTooSmallException.class)
    public void testInvGamma1pm1Precondition1() {

        Gamma.invGamma1pm1(-0.51);
    }

    @Test(expected = NumberIsTooLargeException.class)
    public void testInvGamma1pm1Precondition2() {

        Gamma.invGamma1pm1(1.51);
    }

    private static final double[][] LOG_GAMMA1P_REF = {
        { - 0.5 , .5723649429247001 },
        { - 0.375 , .3608294954889402 },
        { - 0.25 , .2032809514312954 },
        { - 0.125 , .08585870722533433 },
        { 0.0 , 0.0 },
        { 0.125 , - .06002318412603958 },
        { 0.25 , - .09827183642181316 },
        { 0.375 , - .1177552707410788 },
        { 0.5 , - .1207822376352452 },
        { 0.625 , - .1091741337567954 },
        { 0.75 , - .08440112102048555 },
        { 0.875 , - 0.0476726853991883 },
        { 1.0 , 0.0 },
        { 1.125 , .05775985153034387 },
        { 1.25 , .1248717148923966 },
        { 1.375 , .2006984603774558 },
        { 1.5 , .2846828704729192 },
    };

    @Test
    public void testLogGamma1p() {

        final int ulps = 3;
        for (int i = 0; i < LOG_GAMMA1P_REF.length; i++) {
            final double[] ref = LOG_GAMMA1P_REF[i];
            final double x = ref[0];
            final double expected = ref[1];
            final double actual = Gamma.logGamma1p(x);
            final double tol = ulps * FastMath.ulp(expected);
            Assert.assertEquals(Double.toString(x), expected, actual, tol);
        }
    }

    @Test(expected = NumberIsTooSmallException.class)
    public void testLogGamma1pPrecondition1() {

        Gamma.logGamma1p(-0.51);
    }

    @Test(expected = NumberIsTooLargeException.class)
    public void testLogGamma1pPrecondition2() {

        Gamma.logGamma1p(1.51);
    }

    /**
     * Reference data for the {@link Gamma#gamma(double)} function. This
     * data was generated with the following <a
     * href="http://maxima.sourceforge.net/">Maxima</a> script.
     *
     * <pre>
     * kill(all);
     *
     * fpprec : 64;
     *
     * EPSILON : 10**(-fpprec + 1);
     * isInteger(x) := abs(x - floor(x)) <= EPSILON * abs(x);
     *
     * x : makelist(bfloat(i / 8), i, -160, 160);
     * x : append(x, makelist(bfloat(i / 2), i, 41, 200));
     *
     * for i : 1 while i <= length(x) do if not(isInteger(x[i])) then
     *     print("{", float(x[i]), ",", float(gamma(x[i])), "},");
     * </pre>
     */
    private final static double[][] GAMMA_REF = {
        { - 19.875 , 4.920331854832504e-18 },
        { - 19.75 , 3.879938752480031e-18 },
        { - 19.625 , 4.323498423815027e-18 },
        { - 19.5 , 5.811045977502237e-18 },
        { - 19.375 , 9.14330910942125e-18 },
        { - 19.25 , 1.735229114436739e-17 },
        { - 19.125 , 4.653521565668223e-17 },
        { - 18.875 , - 9.779159561479603e-17 },
        { - 18.75 , - 7.662879036148061e-17 },
        { - 18.625 , - 8.484865656736991e-17 },
        { - 18.5 , - 1.133153965612936e-16 },
        { - 18.375 , - 1.771516139950367e-16 },
        { - 18.25 , - 3.340316045290721e-16 },
        { - 18.125 , - 8.899859994340475e-16 },
        { - 17.875 , 1.845816367229275e-15 },
        { - 17.75 , 1.436789819277761e-15 },
        { - 17.625 , 1.580306228567265e-15 },
        { - 17.5 , 2.096334836383932e-15 },
        { - 17.375 , 3.255160907158799e-15 },
        { - 17.25 , 6.096076782655566e-15 },
        { - 17.125 , 1.613099623974211e-14 },
        { - 16.875 , - 3.29939675642233e-14 },
        { - 16.75 , - 2.550301929218027e-14 },
        { - 16.625 , - 2.785289727849803e-14 },
        { - 16.5 , - 3.66858596367188e-14 },
        { - 16.375 , - 5.655842076188414e-14 },
        { - 16.25 , - 1.051573245008085e-13 },
        { - 16.125 , - 2.762433106055837e-13 },
        { - 15.875 , 5.567732026462681e-13 },
        { - 15.75 , 4.271755731440195e-13 },
        { - 15.625 , 4.630544172550298e-13 },
        { - 15.5 , 6.053166840058604e-13 },
        { - 15.375 , 9.261441399758529e-13 },
        { - 15.25 , 1.708806523138138e-12 },
        { - 15.125 , 4.454423383515037e-12 },
        { - 14.875 , - 8.838774592009505e-12 },
        { - 14.75 , - 6.728015277018307e-12 },
        { - 14.625 , - 7.235225269609841e-12 },
        { - 14.5 , - 9.382408602090835e-12 },
        { - 14.375 , - 1.423946615212874e-11 },
        { - 14.25 , - 2.605929947785661e-11 },
        { - 14.125 , - 6.737315367566492e-11 },
        { - 13.875 , 1.314767720561414e-10 },
        { - 13.75 , 9.923822533602004e-11 },
        { - 13.625 , 1.058151695680439e-10 },
        { - 13.5 , 1.360449247303171e-10 },
        { - 13.375 , 2.046923259368506e-10 },
        { - 13.25 , 3.713450175594567e-10 },
        { - 13.125 , 9.516457956687671e-10 },
        { - 12.875 , - 1.8242402122789617e-9 },
        { - 12.75 , - 1.3645255983702756e-9 },
        { - 12.625 , - 1.4417316853645984e-9 },
        { - 12.5 , - 1.836606483859281e-9 },
        { - 12.375 , - 2.7377598594053765e-9 },
        { - 12.25 , - 4.9203214826628017e-9 },
        { - 12.125 , - 1.2490351068152569e-8 },
        { - 11.875 , 2.3487092733091633e-8 },
        { - 11.75 , 1.7397701379221012e-8 },
        { - 11.625 , 1.8201862527728055e-8 },
        { - 11.5 , 2.295758104824101e-8 },
        { - 11.375 , 3.3879778260141535e-8 },
        { - 11.25 , 6.027393816261931e-8 },
        { - 11.125 , 1.5144550670134987e-7 },
        { - 10.875 , - 2.7890922620546316e-7 },
        { - 10.75 , - 2.044229912058469e-7 },
        { - 10.625 , - 2.1159665188483867e-7 },
        { - 10.5 , - 2.640121820547716e-7 },
        { - 10.375 , - 3.8538247770911e-7 },
        { - 10.25 , - 6.780818043294673e-7 },
        { - 10.125 , - 1.6848312620525174e-6 },
        { - 9.875 , 3.0331378349844124e-6 },
        { - 9.75 , 2.1975471554628537e-6 },
        { - 9.625 , 2.2482144262764103e-6 },
        { - 9.5 , 2.772127911575102e-6 },
        { - 9.375 , 3.998343206232017e-6 },
        { - 9.25 , 6.95033849437704e-6 },
        { - 9.125 , 1.7058916528281737e-5 },
        { - 8.875 , - 2.9952236120471065e-5 },
        { - 8.75 , - 2.1426084765762826e-5 },
        { - 8.625 , - 2.163906385291045e-5 },
        { - 8.5 , - 2.633521515996347e-5 },
        { - 8.375 , - 3.748446755842515e-5 },
        { - 8.25 , - 6.429063107298763e-5 },
        { - 8.125 , - 1.5566261332057085e-4 },
        { - 7.875 , 2.658260955691807e-4 },
        { - 7.75 , 1.874782417004247e-4 },
        { - 7.625 , 1.8663692573135265e-4 },
        { - 7.5 , 2.238493288596895e-4 },
        { - 7.375 , 3.1393241580181064e-4 },
        { - 7.25 , 5.303977063521479e-4 },
        { - 7.125 , .001264758733229638 },
        { - 6.875 , - .002093380502607298 },
        { - 6.75 , - .001452956373178292 },
        { - 6.625 , - .001423106558701564 },
        { - 6.5 , - .001678869966447671 },
        { - 6.375 , - .002315251566538353 },
        { - 6.25 , - .003845383371053072 },
        { - 6.125 , - .009011405974261174 },
        { - 5.875 , .01439199095542518 },
        { - 5.75 , .009807455518953468 },
        { - 5.625 , .009428080951397862 },
        { - 5.5 , .01091265478190986 },
        { - 5.375 , 0.014759728736682 },
        { - 5.25 , 0.0240336460690817 },
        { - 5.125 , .05519486159234969 },
        { - 4.875 , - 0.0845529468631229 },
        { - 4.75 , - .05639286923398244 },
        { - 4.625 , - .05303295535161297 },
        { - 4.5 , - .06001960130050425 },
        { - 4.375 , - .07933354195966577 },
        { - 4.25 , - .1261766418626789 },
        { - 4.125 , - .2828736656607921 },
        { - 3.875 , .4121956159577241 },
        { - 3.75 , .2678661288614166 },
        { - 3.625 , 0.24527741850121 },
        { - 3.5 , .2700882058522691 },
        { - 3.375 , .3470842460735378 },
        { - 3.25 , .5362507279163854 },
        { - 3.125 , 1.166853870850768 },
        { - 2.875 , - 1.597258011836181 },
        { - 2.75 , - 1.004497983230312 },
        { - 2.625 , - .8891306420668862 },
        { - 2.5 , - .9453087204829419 },
        { - 2.375 , - 1.17140933049819 },
        { - 2.25 , - 1.742814865728253 },
        { - 2.125 , - 3.646418346408649 },
        { - 1.875 , 4.59211678402902 },
        { - 1.75 , 2.762369453883359 },
        { - 1.625 , 2.333967935425576 },
        { - 1.5 , 2.363271801207355 },
        { - 1.375 , 2.782097159933201 },
        { - 1.25 , 3.921333447888569 },
        { - 1.125 , 7.748638986118379 },
        { - 0.875 , - 8.610218970054413 },
        { - 0.75 , - 4.834146544295877 },
        { - 0.625 , - 3.792697895066561 },
        { - 0.5 , - 3.544907701811032 },
        { - 0.375 , - 3.825383594908152 },
        { - 0.25 , - 4.901666809860711 },
        { - 0.125 , - 8.717218859383175 },
        { 0.125 , 7.533941598797612 },
        { 0.25 , 3.625609908221908 },
        { 0.375 , 2.370436184416601 },
        { 0.5 , 1.772453850905516 },
        { 0.625 , 1.434518848090557 },
        { 0.75 , 1.225416702465178 },
        { 0.875 , 1.089652357422897 },
        { 1.0 , 1.0 },
        { 1.125 , .9417426998497015 },
        { 1.25 , 0.906402477055477 },
        { 1.375 , .8889135691562253 },
        { 1.5 , 0.886226925452758 },
        { 1.625 , 0.896574280056598 },
        { 1.75 , .9190625268488832 },
        { 1.875 , .9534458127450348 },
        { 2.0 , 1.0 },
        { 2.125 , 1.059460537330914 },
        { 2.25 , 1.133003096319346 },
        { 2.375 , 1.22225615758981 },
        { 2.5 , 1.329340388179137 },
        { 2.625 , 1.456933205091972 },
        { 2.75 , 1.608359421985546 },
        { 2.875 , 1.78771089889694 },
        { 3.0 , 2.0 },
        { 3.125 , 2.251353641828193 },
        { 3.25 , 2.549256966718529 },
        { 3.375 , 2.902858374275799 },
        { 3.5 , 3.323350970447843 },
        { 3.625 , 3.824449663366426 },
        { 3.75 , 4.422988410460251 },
        { 3.875 , 5.139668834328703 },
        { 4.0 , 6.0 },
        { 4.125 , 7.035480130713102 },
        { 4.25 , 8.28508514183522 },
        { 4.375 , 9.797147013180819 },
        { 4.5 , 11.63172839656745 },
        { 4.625 , 13.86363002970329 },
        { 4.75 , 16.58620653922594 },
        { 4.875 , 19.91621673302373 },
        { 5.0 , 24.0 },
        { 5.125 , 29.02135553919155 },
        { 5.25 , 35.21161185279968 },
        { 5.375 , 42.86251818266609 },
        { 5.5 , 52.34277778455352 },
        { 5.625 , 64.11928888737773 },
        { 5.75 , 78.78448106132322 },
        { 5.875 , 97.09155657349066 },
        { 6.0 , 120.0 },
        { 6.125 , 148.7344471383567 },
        { 6.25 , 184.8609622271983 },
        { 6.375 , 230.3860352318302 },
        { 6.5 , 287.8852778150443 },
        { 6.625 , 360.6709999914997 },
        { 6.75 , 453.0107661026085 },
        { 6.875 , 570.4128948692577 },
        { 7.0 , 720.0 },
        { 7.125 , 910.9984887224346 },
        { 7.25 , 1155.38101391999 },
        { 7.375 , 1468.710974602918 },
        { 7.5 , 1871.254305797788 },
        { 7.625 , 2389.445374943686 },
        { 7.75 , 3057.822671192607 },
        { 7.875 , 3921.588652226146 },
        { 8.0 , 5040.0 },
        { 8.125 , 6490.864232147346 },
        { 8.25 , 8376.512350919926 },
        { 8.375 , 10831.74343769652 },
        { 8.5 , 14034.40729348341 },
        { 8.625 , 18219.5209839456 },
        { 8.75 , 23698.12570174271 },
        { 8.875 , 30882.5106362809 },
        { 9.0 , 40320.0 },
        { 9.125 , 52738.27188619719 },
        { 9.25 , 69106.22689508938 },
        { 9.375 , 90715.85129070834 },
        { 9.5 , 119292.461994609 },
        { 9.625 , 157143.3684865308 },
        { 9.75 , 207358.5998902487 },
        { 9.875 , 274082.281896993 },
        { 10.0 , 362880.0 },
        { 10.125 , 481236.7309615494 },
        { 10.25 , 639232.5987795768 },
        { 10.375 , 850461.1058503906 },
        { 10.5 , 1133278.388948786 },
        { 10.625 , 1512504.921682859 },
        { 10.75 , 2021746.348929925 },
        { 10.875 , 2706562.533732806 },
        { 11.0 , 3628800.0 },
        { 11.125 , 4872521.900985687 },
        { 11.25 , 6552134.137490662 },
        { 11.375 , 8823533.973197803 },
        { 11.5 , 1.1899423083962249e+7 },
        { 11.625 , 1.6070364792880382e+7 },
        { 11.75 , 2.1733773250996688e+7 },
        { 11.875 , 2.943386755434427e+7 },
        { 12.0 , 3.99168e+7 },
        { 12.125 , 5.420680614846578e+7 },
        { 12.25 , 7.371150904676994e+7 },
        { 12.375 , 1.0036769894512501e+8 },
        { 12.5 , 1.3684336546556586e+8 },
        { 12.625 , 1.8681799071723443e+8 },
        { 12.75 , 2.5537183569921107e+8 },
        { 12.875 , 3.4952717720783816e+8 },
        { 13.0 , 4.790016e+8 },
        { 13.125 , 6.572575245501475e+8 },
        { 13.25 , 9.029659858229319e+8 },
        { 13.375 , 1.2420502744459219e+9 },
        { 13.5 , 1.7105420683195732e+9 },
        { 13.625 , 2.3585771328050845e+9 },
        { 13.75 , 3.2559909051649416e+9 },
        { 13.875 , 4.500162406550916e+9 },
        { 14.0 , 6.2270208e+9 },
        { 14.125 , 8.626505009720685e+9 },
        { 14.25 , 1.196429931215385e+10 },
        { 14.375 , 1.66124224207142e+10 },
        { 14.5 , 2.309231792231424e+10 },
        { 14.625 , 3.213561343446927e+10 },
        { 14.75 , 4.476987494601794e+10 },
        { 14.875 , 6.243975339089396e+10 },
        { 15.0 , 8.71782912e+10 },
        { 15.125 , 1.218493832623047e+11 },
        { 15.25 , 1.704912651981923e+11 },
        { 15.375 , 2.388035722977667e+11 },
        { 15.5 , 3.348386098735565e+11 },
        { 15.625 , 4.699833464791132e+11 },
        { 15.75 , 6.603556554537646e+11 },
        { 15.875 , 9.287913316895475e+11 },
        { 16.0 , 1.307674368e+12 },
        { 16.125 , 1.842971921842358e+12 },
        { 16.25 , 2.599991794272433e+12 },
        { 16.375 , 3.671604924078163e+12 },
        { 16.5 , 5.189998453040126e+12 },
        { 16.625 , 7.343489788736144e+12 },
        { 16.75 , 1.040060157339679e+13 },
        { 16.875 , 1.474456239057157e+13 },
        { 17.0 , 2.0922789888e+13 },
        { 17.125 , 2.971792223970803e+13 },
        { 17.25 , 4.224986665692704e+13 },
        { 17.375 , 6.012253063177992e+13 },
        { 17.5 , 8.563497447516206e+13 },
        { 17.625 , 1.220855177377384e+14 },
        { 17.75 , 1.742100763543963e+14 },
        { 17.875 , 2.488144903408952e+14 },
        { 18.0 , 3.55687428096e+14 },
        { 18.125 , 5.08919418355e+14 },
        { 18.25 , 7.288101998319914e+14 },
        { 18.375 , 1.044628969727176e+15 },
        { 18.5 , 1.498612053315336e+15 },
        { 18.625 , 2.151757250127639e+15 },
        { 18.75 , 3.092228855290534e+15 },
        { 18.875 , 4.447559014843502e+15 },
        { 19.0 , 6.402373705728e+15 },
        { 19.125 , 9.224164457684374e+15 },
        { 19.25 , 1.330078614693384e+16 },
        { 19.375 , 1.919505731873686e+16 },
        { 19.5 , 2.772432298633372e+16 },
        { 19.625 , 4.007647878362728e+16 },
        { 19.75 , 5.797929103669752e+16 },
        { 19.875 , 8.39476764051711e+16 },
        { 20.0 , 1.21645100408832e+17 },
        { 20.5 , 5.406242982335075e+17 },
        { 21.0 , 2.43290200817664e+18 },
        { 21.5 , 1.10827981137869e+19 },
        { 22.0 , 5.109094217170944e+19 },
        { 22.5 , 2.382801594464184e+20 },
        { 23.0 , 1.124000727777608e+21 },
        { 23.5 , 5.361303587544415e+21 },
        { 24.0 , 2.585201673888498e+22 },
        { 24.5 , 1.259906343072938e+23 },
        { 25.0 , 6.204484017332395e+23 },
        { 25.5 , 3.086770540528697e+24 },
        { 26.0 , 1.551121004333099e+25 },
        { 26.5 , 7.871264878348176e+25 },
        { 27.0 , 4.032914611266056e+26 },
        { 27.5 , 2.085885192762267e+27 },
        { 28.0 , 1.088886945041835e+28 },
        { 28.5 , 5.736184280096234e+28 },
        { 29.0 , 3.048883446117139e+29 },
        { 29.5 , 1.634812519827427e+30 },
        { 30.0 , 8.841761993739702e+30 },
        { 30.5 , 4.822696933490909e+31 },
        { 31.0 , 2.65252859812191e+32 },
        { 31.5 , 1.470922564714727e+33 },
        { 32.0 , 8.222838654177922e+33 },
        { 32.5 , 4.633406078851391e+34 },
        { 33.0 , 2.631308369336935e+35 },
        { 33.5 , 1.505856975626702e+36 },
        { 34.0 , 8.683317618811885e+36 },
        { 34.5 , 5.044620868349451e+37 },
        { 35.0 , 2.952327990396041e+38 },
        { 35.5 , 1.740394199580561e+39 },
        { 36.0 , 1.033314796638614e+40 },
        { 36.5 , 6.178399408510991e+40 },
        { 37.0 , 3.719933267899013e+41 },
        { 37.5 , 2.255115784106512e+42 },
        { 38.0 , 1.376375309122634e+43 },
        { 38.5 , 8.456684190399419e+43 },
        { 39.0 , 5.230226174666011e+44 },
        { 39.5 , 3.255823413303776e+45 },
        { 40.0 , 2.039788208119745e+46 },
        { 40.5 , 1.286050248254992e+47 },
        { 41.0 , 8.159152832478976e+47 },
        { 41.5 , 5.208503505432716e+48 },
        { 42.0 , 3.345252661316381e+49 },
        { 42.5 , 2.161528954754577e+50 },
        { 43.0 , 1.40500611775288e+51 },
        { 43.5 , 9.186498057706953e+51 },
        { 44.0 , 6.041526306337383e+52 },
        { 44.5 , 3.996126655102524e+53 },
        { 45.0 , 2.658271574788449e+54 },
        { 45.5 , 1.778276361520623e+55 },
        { 46.0 , 1.196222208654802e+56 },
        { 46.5 , 8.091157444918836e+56 },
        { 47.0 , 5.502622159812088e+57 },
        { 47.5 , 3.762388211887259e+58 },
        { 48.0 , 2.586232415111682e+59 },
        { 48.5 , 1.787134400646448e+60 },
        { 49.0 , 1.241391559253607e+61 },
        { 49.5 , 8.667601843135273e+61 },
        { 50.0 , 6.082818640342675e+62 },
        { 50.5 , 4.290462912351959e+63 },
        { 51.0 , 3.041409320171338e+64 },
        { 51.5 , 2.16668377073774e+65 },
        { 52.0 , 1.551118753287382e+66 },
        { 52.5 , 1.115842141929936e+67 },
        { 53.0 , 8.065817517094388e+67 },
        { 53.5 , 5.858171245132164e+68 },
        { 54.0 , 4.274883284060025e+69 },
        { 54.5 , 3.134121616145708e+70 },
        { 55.0 , 2.308436973392413e+71 },
        { 55.5 , 1.70809628079941e+72 },
        { 56.0 , 1.269640335365828e+73 },
        { 56.5 , 9.479934358436728e+73 },
        { 57.0 , 7.109985878048635e+74 },
        { 57.5 , 5.356162912516752e+75 },
        { 58.0 , 4.052691950487721e+76 },
        { 58.5 , 3.079793674697132e+77 },
        { 59.0 , 2.350561331282878e+78 },
        { 59.5 , 1.801679299697822e+79 },
        { 60.0 , 1.386831185456898e+80 },
        { 60.5 , 1.071999183320204e+81 },
        { 61.0 , 8.320987112741391e+81 },
        { 61.5 , 6.485595059087236e+82 },
        { 62.0 , 5.075802138772249e+83 },
        { 62.5 , 3.98864096133865e+84 },
        { 63.0 , 3.146997326038794e+85 },
        { 63.5 , 2.492900600836656e+86 },
        { 64.0 , 1.98260831540444e+87 },
        { 64.5 , 1.582991881531277e+88 },
        { 65.0 , 1.268869321858841e+89 },
        { 65.5 , 1.021029763587673e+90 },
        { 66.0 , 8.247650592082472e+90 },
        { 66.5 , 6.687744951499262e+91 },
        { 67.0 , 5.443449390774431e+92 },
        { 67.5 , 4.447350392747009e+93 },
        { 68.0 , 3.647111091818868e+94 },
        { 68.5 , 3.001961515104231e+95 },
        { 69.0 , 2.48003554243683e+96 },
        { 69.5 , 2.056343637846398e+97 },
        { 70.0 , 1.711224524281413e+98 },
        { 70.5 , 1.429158828303247e+99 },
        { 71.0 , 1.19785716699699e+100 },
        { 71.5 , 1.00755697395379e+101 },
        { 72.0 , 8.50478588567862e+101 },
        { 72.5 , 7.20403236376959e+102 },
        { 73.0 , 6.12344583768861e+103 },
        { 73.5 , 5.22292346373295e+104 },
        { 74.0 , 4.47011546151268e+105 },
        { 74.5 , 3.83884874584372e+106 },
        { 75.0 , 3.30788544151939e+107 },
        { 75.5 , 2.85994231565357e+108 },
        { 76.0 , 2.48091408113954e+109 },
        { 76.5 , 2.15925644831845e+110 },
        { 77.0 , 1.88549470166605e+111 },
        { 77.5 , 1.65183118296361e+112 },
        { 78.0 , 1.45183092028286e+113 },
        { 78.5 , 1.2801691667968e+114 },
        { 79.0 , 1.13242811782063e+115 },
        { 79.5 , 1.00493279593549e+116 },
        { 80.0 , 8.94618213078298e+116 },
        { 80.5 , 7.98921572768712e+117 },
        { 81.0 , 7.15694570462638e+118 },
        { 81.5 , 6.43131866078814e+119 },
        { 82.0 , 5.79712602074737e+120 },
        { 82.5 , 5.24152470854233e+121 },
        { 83.0 , 4.75364333701284e+122 },
        { 83.5 , 4.32425788454742e+123 },
        { 84.0 , 3.94552396972066e+124 },
        { 84.5 , 3.6107553335971e+125 },
        { 85.0 , 3.31424013456535e+126 },
        { 85.5 , 3.05108825688955e+127 },
        { 86.0 , 2.81710411438055e+128 },
        { 86.5 , 2.60868045964056e+129 },
        { 87.0 , 2.42270953836727e+130 },
        { 87.5 , 2.25650859758909e+131 },
        { 88.0 , 2.10775729837953e+132 },
        { 88.5 , 1.97444502289045e+133 },
        { 89.0 , 1.85482642257398e+134 },
        { 89.5 , 1.74738384525805e+135 },
        { 90.0 , 1.65079551609085e+136 },
        { 90.5 , 1.56390854150595e+137 },
        { 91.0 , 1.48571596448176e+138 },
        { 91.5 , 1.41533723006289e+139 },
        { 92.0 , 1.3520015276784e+140 },
        { 92.5 , 1.29503356550754e+141 },
        { 93.0 , 1.24384140546413e+142 },
        { 93.5 , 1.19790604809448e+143 },
        { 94.0 , 1.15677250708164e+144 },
        { 94.5 , 1.12004215496834e+145 },
        { 95.0 , 1.08736615665674e+146 },
        { 95.5 , 1.05843983644508e+147 },
        { 96.0 , 1.03299784882391e+148 },
        { 96.5 , 1.01081004380505e+149 },
        { 97.0 , 9.9167793487095e+149 },
        { 97.5 , 9.75431692271873e+150 },
        { 98.0 , 9.61927596824821e+151 },
        { 98.5 , 9.51045899965076e+152 },
        { 99.0 , 9.42689044888325e+153 },
        { 99.5 , 9.367802114656e+154 },
        { 100.0 , 9.33262154439441e+155 },
    };

    @Test
    public void testGamma() {

        for (int i = 0; i < GAMMA_REF.length; i++) {
            final double[] ref = GAMMA_REF[i];
            final double x = ref[0];
            final double expected = ref[1];
            final double actual = Gamma.gamma(x);
            final double absX = FastMath.abs(x);
            final int ulps;
            if (absX <= 8.0) {
                ulps = 3;
            } else if (absX <= 20.0) {
                ulps = 5;
            } else if (absX <= 30.0) {
                ulps = 50;
            } else if (absX <= 50.0) {
                ulps = 180;
            } else {
                ulps = 500;
            }
            final double tol = ulps * FastMath.ulp(expected);
            Assert.assertEquals(Double.toString(x), expected, actual, tol);
        }
    }

    private void checkRelativeError(String msg, double expected, double actual, double tolerance) {
        Assert.assertEquals(msg, expected, actual, FastMath.abs(tolerance * actual));
    }
}
