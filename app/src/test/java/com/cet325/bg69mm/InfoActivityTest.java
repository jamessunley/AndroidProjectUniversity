package com.cet325.bg69mm;

import com.cet325.bg69mm.utils.PricingUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jamessunley on 12/01/2018.
 */
public class InfoActivityTest {

    //test the discount calculator
    @Test
    public void discountCalculator() throws Exception {

        Double actual = PricingUtils.DiscountCalculator(10.0, 0.75);
        // correct expected value is7.5
        double expected = 7.5;

        assertEquals("discount calculator Failed", expected,
                actual, 0.0);
    }

    //test the converter
    @Test
    public void converter()throws Exception{

        Double actual = PricingUtils.CurrencyConverter(0.88983, 10.0);

        //correct expecyed value is 8.8983
        double expected = 8.8983;

        assertEquals("converter failed", expected, actual, 0.0);
    }

    //test the output text for students
    @Test
    public void studentText()throws Exception{

        String actual = PricingUtils.StudentText(10.0, 0.7, "£");

        String expected = "Student: £" + 7.0;

        assertEquals("converter failed", expected, actual);
    }

    //test the spinner output for students
    @Test
    public void studentSpinner()throws Exception{


        String actual = PricingUtils.SpinnerToDoStudent("10", 0.0, 0.88983, "£", 0.7);

        String expected = "Student: £" + 6.23;

        assertEquals("converter failed", expected, actual);
    }

    //test the spinner output for Adults
    @Test
    public void adultSpinner()throws Exception{


        String actual = PricingUtils.SpinnerToDoAdult("10", 0.0, 0.88983, "£");

        String expected = "Adult: £" + 8.9;

        assertEquals("converter failed", expected, actual);
    }
}