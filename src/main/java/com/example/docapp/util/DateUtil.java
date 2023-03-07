package com.example.docapp.util;

import com.example.docapp.exception.InvalidDateFormatException;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String INVALID_DATE_FORMAT = "Invalid date format, valid date format is dd-MM-yyyy";

    public static Date stringToDate(String dateStr) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT);
        }
    }

    public static String dateToString(Date date) {
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}
