package com.solution.services.handlers.logprocessor.regex;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexMatcher {

    public boolean textMatches(List<String> patterns, String text) {

        Pattern pattern;
        Matcher matcher;
        for (String regex : patterns) {
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(text);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    public List<String> readFileAndfilterSensitiveLines(List<String> regexPatterns, String rawLogPath) {
        List<String> out = new ArrayList<>();
        File f = new File(rawLogPath);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (textMatches(regexPatterns, line)) {
                    out.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
}
