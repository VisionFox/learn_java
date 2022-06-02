package com.lusir;


import java.util.HashMap;
import java.util.Map;

/**
 * "abab" == "bigoaibigoai", a = "bi", b = "goai"; a = "big", b = "oai"
 * "abba" != "bigoaibigoai"
 * "ababc" != "bigoaibigoai"
 * "abcd" == "bigoaibigoai", a = "bigo", b = "ai", c = "bigo", d = "ai"
 **/
public class Bigo {

    public static void main(String[] args) {
        Bigo bigo = new Bigo();
        System.out.println(bigo.solution("bigoaibigoai".toCharArray(), 0, "abab".toCharArray(), 0, new HashMap<>()));
    }

    private boolean solution(char[] sChars, int sStart, char[] tChars, int tStart, Map<String, String> matchMap) {
        int sLessLen = sChars.length - sStart;
        int tLessLen = tChars.length - tStart;
        if (tLessLen>sLessLen|| sLessLen%tLessLen!=0){
            return false;
        }

        if (sStart>=sChars.length){

        }



        for (int i = 0; i < sChars.length; i++) {

        }
        return false;
    }
}
