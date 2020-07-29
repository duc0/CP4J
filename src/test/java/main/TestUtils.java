/*
 * Copyright (c) CP4J Project
 */

package main;

import com.vb.io.FastScanner;
import com.vb.io.FastWriter;
import com.vb.task.CPTaskSolver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public final class TestUtils {
    static FastScanner getScanner(String inputFile) {
        try {
            return new FastScanner(new FileInputStream("testdata/" + inputFile + ".in"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static String fileToString(String fileName) {
        try {
            return String.join("\n", Files.readAllLines(Paths.get(fileName), StandardCharsets.US_ASCII)
                    .stream().map(s -> s.trim()).collect(Collectors.toList()));
        } catch (IOException e) {
            return null;
        }
    }

    static FastWriter getWriter(StringWriter sw) {
        return new FastWriter(sw);
    }

    static void checkTest(CPTaskSolver cpTaskSolver, String testName) {
        StringWriter sw = new StringWriter();
        try {
            cpTaskSolver.solve(0, getScanner(testName), getWriter(sw));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(sw.toString().trim(), fileToString("testData/" + testName + ".out").trim());
    }
}
