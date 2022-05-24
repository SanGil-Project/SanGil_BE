package com.project.sangil_be;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class test {
    public static void main(String[] args) throws ParseException {
        LocalDate date = LocalDate.now();
        LocalDate date1 = LocalDate.parse("2022-05-24");
        if (date.isAfter(date1)|| date.isEqual(date1)) {
            System.out.println(11);
        }
        System.out.println(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(date);
        Date day = dateFormat.parse(String.valueOf(date));
        Date day2 = dateFormat.parse("2022-05-25");
        if (day2.after(day)) {
            System.out.println(11);
        }
    }
}
