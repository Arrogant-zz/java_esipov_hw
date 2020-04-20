package ru.sbrf.esipov.hw;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    public static void main(String[] args) {
        parse("./logs/UseSerialGC.log");
        parse("./logs/UseG1GC.log");
    }

    public static void parse(String filename) {
        File file = new File(filename);

        int MajorCnt = 0;
        int MinorCnt = 0;
        double MajorTime = 0;
        double MinorTime = 0;
        double MajorOtherTime = 0;

        System.out.println(filename);

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8.name())) {
            while (sc.hasNextLine()){
                Pattern pattern = Pattern.compile("^.*(Pause Young|Pause Full).*\\) ([0-9]+\\.[0-9]+)ms$");
                String nL = sc.nextLine();
                Matcher matcher = pattern.matcher(nL);
                if (matcher.find()) {
                    if (matcher.group(1).equals("Pause Full")) {
                        MajorCnt++;
                        MajorTime += Double.parseDouble(matcher.group(2));
                    }
                    else {
                        MinorCnt++;
                        MinorTime += Double.parseDouble(matcher.group(2));
                    }
                }
                else {
                    pattern = Pattern.compile("^.*\\) ([0-9]+\\.[0-9]+)ms$");
                    matcher = pattern.matcher(nL);
                    if (matcher.find()) {
                        MajorOtherTime += Double.parseDouble(matcher.group(1));
                    }

                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Major count: " + MajorCnt);
        System.out.println("Major time: " + MajorTime + " ms | Avg: " + (MajorTime / MajorCnt) + " ms");
        System.out.println("Major other time: " + MajorOtherTime + " ms");
        System.out.println("Minor count: " + MinorCnt);
        System.out.println("Minor time: " + MinorTime + " ms | Avg: " + (MinorTime / MinorCnt) + " ms");
    }
}
