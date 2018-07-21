package com.cet325.bg69mm.utils;

/**
 * Created by jamessunley on 14/01/2018.
 */

//utilities class for all of the conversions associated with the information activity
public class PricingUtils {

    //method to calculate the discount to be given to students
    //passes in discount parameter and returns discount value
    public static double DiscountCalculator(Double result, Double discount){

        double discountValue = result * discount;
        discountValue = (double) Math.round(discountValue * 100)/100;
        return discountValue;

    }

    //method to calculate the new price of the tickets using the conversion rates
    //passes in conversion and user input for the new cost parameter
    //returns the result, the new price, in the new currency
    public static double CurrencyConverter(Double conversion, Double priceChange){
        double result;
        result = conversion * priceChange;
        return result;
    }

    //method to display the new price of the student ticket
    //passes in the user input for the new cost parameter, the value of the discount and the string of the currency symbol e.g "£"
    //returns the string to be displayed in the student price text field
    public static String StudentText(Double priceChange, Double discount, String currency){

        String text;
        double discountValue = priceChange * discount;

        text = "Student: " + currency + discountValue;
        return text;
    }

    public static String SpinnerToDoStudent(String input, Double result, Double conversion, String currency, Double discount){

        //converts string to double
        Double priceChange = Double.parseDouble(input);

        //calls utils to convert the currency, returns the result as a double
        result = (PricingUtils.CurrencyConverter(conversion, priceChange));

        //calls utils to calculate the discount needed for the student pricing
        double discountValue=(PricingUtils.DiscountCalculator(result, discount));
        //rounds the result to 2 decimal places
        result = (double) Math.round(result * 100)/100;

        String price;

        price = "Student: " + currency + discountValue;

        return price;

    }

    public static String SpinnerToDoAdult(String input, Double result, Double conversion, String currency){

        //converts string to double
        Double priceChange = Double.parseDouble(input);

        //calls utils to convert the currency, returns the result as a double
        result = (PricingUtils.CurrencyConverter(conversion, priceChange));

        //rounds the result to 2 decimal places
        result = (double) Math.round(result * 100)/100;

        String price;

        price = "Adult: " + currency + result.toString();

        return price;

    }

}
