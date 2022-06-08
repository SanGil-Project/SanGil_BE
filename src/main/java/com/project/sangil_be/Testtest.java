package com.project.sangil_be;

import java.time.LocalDate;
import java.time.ZoneId;

public class Testtest {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        String jjj = now.toString();
        System.out.println(jjj);
        String year = String.valueOf(now).split("-")[0];
        String month = String.valueOf(now).split("-")[1];
        String day = String.valueOf(now).split("-")[2];
        String date = year + "-" + month + "-" + day;
        System.out.println(date);
    }
}
