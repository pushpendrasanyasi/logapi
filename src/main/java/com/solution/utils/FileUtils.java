package com.solution.utils;

import com.solution.common.constants.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileUtils {

    public static List<String> readFile(String absolutePath) {
        List<String> fileOutput = new ArrayList<>();
        File f = new File(ApplicationProperties.REGEX_FILE_PATH);
        FileReader fr = null;
        try {
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                fileOutput.add(line);
            }
        } catch (FileNotFoundException e) {
            log.error("File not found at path {}", absolutePath);
        } catch (IOException e) {
            log.error("Error occured while reading file {} , {}", absolutePath, e);
        }

        return fileOutput;
    }

    public static List<String> getFileNamesInDirectory(String dirPath) {
        File f = new File(dirPath);
        if (f.exists() && f.isDirectory() && ArrayUtils.isNotEmpty(f.listFiles())) {
            return Arrays.stream(f.listFiles()).map(File::getName).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
