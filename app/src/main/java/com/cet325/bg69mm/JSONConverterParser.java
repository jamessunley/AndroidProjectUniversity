package com.cet325.bg69mm;

import com.cet325.bg69mm.model.Converter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jamessunley on 04/01/2018.
 */

public class JSONConverterParser {

    public static Converter getConverter(String data) throws JSONException {

        //create out JSONObject from the data
        JSONObject myresponse = new JSONObject(data.toString());

        //start extracting the info
        Converter conv = new Converter();

        //JSONObject baseObj = getObject("base", myresponse);
        conv.setBase(getString("base", myresponse));

        // get Rates info

        JSONObject rates_object = new JSONObject(myresponse.getJSONObject("rates").toString());

        conv.rates.setAUD(getFloat("AUD", rates_object));
        conv.rates.setBGN(getFloat("BGN", rates_object));
        conv.rates.setBRL(getFloat("BRL", rates_object));
        conv.rates.setCAD(getFloat("CAD", rates_object));
        conv.rates.setCHF(getFloat("CHF", rates_object));
        conv.rates.setCNY(getFloat("CNY", rates_object));
        conv.rates.setCZK(getFloat("CZK", rates_object));
        conv.rates.setDKK(getFloat("DKK", rates_object));
        conv.rates.setGBP(getFloat("GBP", rates_object));
        conv.rates.setHKD(getFloat("HKD", rates_object));
        conv.rates.setHUF(getFloat("HUF", rates_object));
        conv.rates.setIDR(getFloat("IDR", rates_object));
        conv.rates.setILS(getFloat("ILS", rates_object));
        conv.rates.setINR(getFloat("INR", rates_object));
        conv.rates.setJPY(getFloat("JPY", rates_object));
        conv.rates.setMXN(getFloat("MXN", rates_object));
        conv.rates.setMYR(getFloat("MYR", rates_object));
        conv.rates.setNOK(getFloat("NOK", rates_object));
        conv.rates.setNZD(getFloat("NZD", rates_object));
        conv.rates.setPHP(getFloat("PHP", rates_object));
        conv.rates.setPLN(getFloat("PLN", rates_object));
        conv.rates.setRON(getFloat("RON", rates_object));
        conv.rates.setRUB(getFloat("RUB", rates_object));
        conv.rates.setSEK(getFloat("SEK", rates_object));
        conv.rates.setSGD(getFloat("SGD", rates_object));
        conv.rates.setTHB(getFloat("THB", rates_object));
        conv.rates.setTRY(getFloat("TRY", rates_object));
        conv.rates.setUSD(getFloat("USD", rates_object));
        conv.rates.setZAR(getFloat("ZAR", rates_object));

        return conv;

    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        return jObj.getJSONObject(tagName);
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
