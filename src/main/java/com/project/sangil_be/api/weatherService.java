package com.project.sangil_be.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class weatherService {

    private void weather() throws IOException, ParseException {

        double lat = 37.556671;
        double lng = 127.198374;

        // 위도 경도를 x,y 좌표로 바꿔줌
        GpsTransfer gpsTransfer = new GpsTransfer();
        gpsTransfer.setLat(lat);
        gpsTransfer.setLng(lng);
        gpsTransfer.transfer(gpsTransfer, 0);

        // 현재 날짜
        LocalDate seoulNow = LocalDate.now(ZoneId.of("Asia/Seoul"));
        String month;
        String day;
        if (seoulNow.getMonthValue() < 10) {
            month = "0" + seoulNow.getMonthValue();
        } else {
            month = String.valueOf(seoulNow.getMonthValue());
        }
        if (seoulNow.getDayOfMonth() < 10) {
            day = "0" + seoulNow.getDayOfMonth();
        } else {
            day = String.valueOf(seoulNow.getDayOfMonth());
        }
        String localDate = String.valueOf(seoulNow.getYear()) + month + day;

        // 현재 시간
        LocalTime now = LocalTime.now(ZoneId.of("Asia/Seoul"));
        String hour = String.valueOf(now).split(":")[0];
        String minute = String.valueOf(now).split(":")[1];
        String time = hour + minute;

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /*URL*/ // 초단기예보조회
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "="
                + "CUnaloj7dqGa0E7B1yinNSBe8arlqRx4Vz9sWrfACPuZk2RnkhAo1wSYHxnZB0q2ephaL1YaanF%2BzpKUF%2FQcIg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(localDate, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(String.valueOf((int) gpsTransfer.getxLat()), "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(String.valueOf((int) gpsTransfer.getyLng()), "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
//        rd.close();
//        conn.disconnect();
//        System.out.println("이게바로" + sb);

        String data = sb.toString();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(data);
        // response 키를 가지고 데이터를 파싱
        JSONObject parse_response = (JSONObject) obj.get("response");
        // response 로 부터 body 찾기
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        // body 로 부터 items 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items");

        // items로 부터 itemlist 를 받기
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        JSONObject weatherInfo = (JSONObject) parse_item.get(0);

        List<String> T1H = new ArrayList<>(); // 기온
        List<String> PTY = new ArrayList<>(); // 강수형태
        List<String> SKY = new ArrayList<>(); // 하늘상태

        for (int i = 0; i < parse_item.size(); i++) {
            weatherInfo = (JSONObject) parse_item.get(i);

            if (weatherInfo.get("category").equals("T1H")) {
                T1H.add((String) weatherInfo.get("fcstValue"));
            }
            if (weatherInfo.get("category").equals("PTY")) {
                PTY.add((String) weatherInfo.get("fcstValue"));
            }
            if (weatherInfo.get("category").equals("SKY")) {
                SKY.add((String) weatherInfo.get("fcstValue"));
            }

        }
        String currentTemp = T1H.get(0)+"℃"; // 현재 기온
        String precipitation = PTY.get(0); // 현재 강수형태
        String skyCondition = SKY.get(0); // 현재 하늘상태

//        // 기온에 따른 문구
//        String tempMsg;
//        if (currentTemp >= 35) {
//            tempMsg = "등산 자제해주세요";
//        } else if (currentTemp < 35 && currentTemp >= 30) {
//            tempMsg = "";
//        } else if (currentTemp < 30 && currentTemp >= 25) {
//            tempMsg = "";
//        } else if (currentTemp < 25 && currentTemp >= 20) {
//            tempMsg = "";
//        } else if (currentTemp < 20 && currentTemp >= 15) {
//            tempMsg = "";
//        } else if (currentTemp < 15 && currentTemp >= 10) {
//            tempMsg = "";
//        } else if (currentTemp < 10 && currentTemp >= 5) {
//            tempMsg = "";
//        } else if (currentTemp < 5 && currentTemp >= 0) {
//            tempMsg = "";
//        } else if (currentTemp < 0 && currentTemp >= -5) {
//            tempMsg = "";
//        } else if (currentTemp < -5 && currentTemp >= -10) {
//            tempMsg = "";
//        } else if (currentTemp < -10 && currentTemp >= -15) {
//            tempMsg = "";
//        } else if (currentTemp < -15 && currentTemp >= -20) {
//            tempMsg = "";
//        } else {
//            tempMsg = "";
//        }

        // 강수 형태 이미지url
        String precipitationUrl;
        if (precipitation.equals("0")) {
            precipitationUrl = "없음";
        } else if (precipitation.equals("1")) {
            precipitationUrl = "비";
        } else if (precipitation.equals("2")) {
            precipitationUrl = "비/눈";
        } else if (precipitation.equals("3")) {
            precipitationUrl = "눈";
        } else if (precipitation.equals("5")) {
            precipitationUrl = "빗방울";
        } else if (precipitation.equals("6")) {
            precipitationUrl = "빗방울눈날림";
        } else if (precipitation.equals("7")) {
            precipitationUrl = "눈날림";
        }

        // 하늘 상태 이미지url
        String skyConditionUrl;
        if (skyCondition.equals("1")) {
            skyConditionUrl = "맑음";
        } else if (skyCondition.equals("3")) {
            skyConditionUrl = "구름많음";
        } else if (skyCondition.equals("4")) {
            skyConditionUrl = "흐림";
        }
    }

}
