package com.manabie.fix_lints_tool.features;

import com.manabie.fix_lints_tool.core.FixLinterCore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FixLintFileLoader {
    private static final String filePathInString = ">>> file paths";
    private static final String filePathOutString = "<<< file paths";
    private static final String variableInString = ">>> variables";
    private static final String variableOutString = "<<< variables";

    private static void overrideFile(String srcPath, String targetPath) {
        try {
            Files.move(new File(srcPath).toPath(), new File(targetPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fixLintWithSingleFile(String filePath, List<String> variables) {
        try {
            // Get file name
            File target = new File(filePath);
            final String fileName = target.getName();

            // Read file & print
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            PrintWriter out = new PrintWriter(fileName);
            String line;
            while ((line = in.readLine()) != null) {
                for (String variable : variables) {
                    line = FixLinterCore.fixLintsForVariableInLine(line, variable);
                }
                out.println(line);
            }

            // Override file
            overrideFile(fileName, filePath);

            // Close the files
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fixAll() {
        try {
            // Read file config
            BufferedReader in = new BufferedReader(new FileReader("config.txt"));

            List<String> filePaths = new ArrayList<>();
            List<String> variables = new ArrayList<>();

            // Read all file paths want to fix lint
            String path;
            while (!(path = in.readLine()).equals(filePathOutString)) {
                if (!path.isEmpty() && !path.equals(filePathInString)) {
                    filePaths.add(path);
                }
            }

            // Read all variables you want to fix lint
            String variable;
            while (!(variable = in.readLine()).equals(variableOutString)) {
                if (!variable.isEmpty() && !variable.equals(variableInString)) {
                    variables.add(variable);
                }
            }

            // Start fix lints
            for (String filePath : filePaths) {
                fixLintWithSingleFile(filePath, variables);
            }

            // Close file
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
