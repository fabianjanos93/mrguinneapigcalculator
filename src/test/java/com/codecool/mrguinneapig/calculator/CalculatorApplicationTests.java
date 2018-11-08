package com.codecool.mrguinneapig.calculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorApplicationTests {


    Calculator calculator;

    @Before
    public void init() {
        calculator = new Calculator();
    }
    @Test
    public void contains0True() {
        String[] testArray = new String[]{"0","1","2"};
        Assert.assertEquals(true,calculator.contains("0", testArray));
    }

    @Test
    public void contains1True() {
        String[] testArray = new String[]{"0","1","2"};
        Assert.assertEquals(true,calculator.contains("1", testArray));
    }

    @Test
    public void contains2True() {
        String[] testArray = new String[]{"0","1","2"};
        Assert.assertEquals(true,calculator.contains("2", testArray));
    }

    @Test
    public void contains3False() {
        String[] testArray = new String[]{"0","1","2"};
        Assert.assertEquals(false,calculator.contains("3", testArray));
    }

    @Test
    public void fromDigitToNumberOnlyOneDigit() {
        ArrayList<String> test = new ArrayList<String>();
        test.addAll(Arrays.asList("0"));
        calculator.fromDigitToNumber(test);

        ArrayList<String> ans = new ArrayList<String>();
        ans.addAll(Arrays.asList("0"));
        Assert.assertEquals(ans,test);
    }

    @Test
    public void fromDigitToNumberMultipleDigit() {
        ArrayList<String> test = new ArrayList<String>();
        test.addAll(Arrays.asList("0","1","2","3"));
        calculator.fromDigitToNumber(test);

        ArrayList<String> ans = new ArrayList<String>();
        ans.addAll(Arrays.asList("0123"));
        Assert.assertEquals(ans,test);
    }

    @Test
    public void fromDigitToNumberOnlyOneLetter() {
        ArrayList<String> test = new ArrayList<String>();
        test.addAll(Arrays.asList("f"));
        calculator.fromDigitToNumber(test);

        ArrayList<String> ans = new ArrayList<String>();
        ans.addAll(Arrays.asList("f"));
        Assert.assertEquals(ans,test);
    }

    @Test
    public void fromDigitToNumber01f23j45() {
        ArrayList<String> test = new ArrayList<String>();
        test.addAll(Arrays.asList("0","1","f","2","3","j","4","5"));
        calculator.fromDigitToNumber(test);

        ArrayList<String> ans = new ArrayList<String>();
        ans.addAll(Arrays.asList("01","f","23","j","45"));
        Assert.assertEquals(ans,test);
    }

    @Test
    public void fromDigitToNumber01fj45() {
        ArrayList<String> test = new ArrayList<String>();
        test.addAll(Arrays.asList("0","1","f","j","4","5"));
        calculator.fromDigitToNumber(test);

        ArrayList<String> ans = new ArrayList<String>();
        ans.addAll(Arrays.asList("01","f","j","45"));
        Assert.assertEquals(ans,test);
    }

    @Test
    public void fromDigitToNumberfj01fj45() {
        ArrayList<String> test = new ArrayList<String>();
        test.addAll(Arrays.asList("f","j","0","1","f","j","4","5"));
        calculator.fromDigitToNumber(test);

        ArrayList<String> ans = new ArrayList<String>();
        ans.addAll(Arrays.asList("f","j","01","f","j","45"));
        Assert.assertEquals(ans,test);
    }

    @Test
    public void fromDigitToNumber01fj45fj() {
        ArrayList<String> test = new ArrayList<String>();
        test.addAll(Arrays.asList("0","1","f","j","4","5","f","j"));
        calculator.fromDigitToNumber(test);

        ArrayList<String> ans = new ArrayList<String>();
        ans.addAll(Arrays.asList("01","f","j","45","f","j"));
        Assert.assertEquals(ans,test);
    }

    @Test
    public void solvabletrue1122354() {
        calculator.setEquation("1122354");
        Assert.assertEquals(true,calculator.solvable());
    }

    @Test
    public void solvableTrue11plus22plus354() {
        calculator.setEquation("11+22+354");
        Assert.assertEquals(true,calculator.solvable());
    }

    @Test
    public void solvableTrue11minus22minus354() {
        calculator.setEquation("11-22-354");
        Assert.assertEquals(true,calculator.solvable());
    }

    @Test
    public void solvableTrue11divide22divide354() {
        calculator.setEquation("11/22/354");
        Assert.assertEquals(true,calculator.solvable());
    }

    @Test
    public void solvableTrue11multiply22multiply354() {
        calculator.setEquation("11*22*354");
        Assert.assertEquals(true,calculator.solvable());
    }

    @Test
    public void solvableFalse11multiplymultiply354() {
        calculator.setEquation("11**354");
        Assert.assertEquals(false,calculator.solvable());
    }

    @Test
    public void solvableFalse11multiply354multiply() {
        calculator.setEquation("11*354*");
        Assert.assertEquals(false,calculator.solvable());
    }

    @Test
    public void solvableFalsemultiply11multiply354() {
        calculator.setEquation("*11*354");
        Assert.assertEquals(false,calculator.solvable());
    }

    @Test
    public void calculator15plus3multipy2minus1() {
        calculator.setEquation("15+3*2-1");
        Assert.assertEquals(20,calculator.solve());
    }

    @Test
    public void calculatorNegative15plus3multipy2minus1minus100() {
        calculator.setEquation("15+3*2-1-100");
        Assert.assertEquals(-80,calculator.solve());
    }

    @Test
    public void calculatorLotOfSpaces() {
        calculator.setEquation("   15 +  3 *  2   -   1 -  100     ");
        Assert.assertEquals(-80,calculator.solve());
    }

    @Test
    public void calculatorBraceletTest() {
        calculator.setEquation("(15)");
        Assert.assertEquals(15,calculator.solve());
    }

    @Test
    public void calculatorBraceletception() {
        calculator.setEquation("((((15))))");
        Assert.assertEquals(15,calculator.solve());
    }

    @Test
    public void calculatorComplicatedBracelet() {
        calculator.setEquation("((1+2)*(1+2)*(1+2))+1");
        Assert.assertEquals(28,calculator.solve());
    }

    @Test
    public void calculatorTrueComplicatedBracelet() {
        calculator.setEquation("((1+2)*(1+2)*(1+2))+1");
        calculator.solve();
        Assert.assertTrue(calculator.isSolvable());
    }

    @Test
    public void calculatorFalseComplicatedBracelet() {
        calculator.setEquation("(((1+2)*(1+2)*(1+2))+1");
        calculator.solve();
        Assert.assertFalse(calculator.isSolvable());
    }

    @Test
    public void calculatorComplicatedBracelet2() {
        calculator.setEquation("((1+2+(1+2*2))*(1+2)*(1+2))+1+(3+2*2)");
        Assert.assertEquals(80,calculator.solve());
    }

    @Test
    public void calculatorLazyWriting() {
        calculator.setEquation("3+4(2/2)");
        Assert.assertEquals(7,calculator.solve());
    }





}
