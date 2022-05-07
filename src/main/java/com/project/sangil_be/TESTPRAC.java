package com.project.sangil_be;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class TESTPRAC {
    public static void main(String[] args) {
        LocalDate seoulNow = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalTime now = LocalTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime aa = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String year = String.valueOf(seoulNow).split("-")[0];
        String month = String.valueOf(seoulNow).split("-")[2];
        System.out.println(month);
        System.out.println(seoulNow);
//        System.out.println(now);
//        System.out.println(aa);
    }
}
