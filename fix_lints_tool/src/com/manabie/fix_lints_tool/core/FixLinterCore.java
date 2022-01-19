package com.manabie.fix_lints_tool.core;

import java.util.Arrays;

public class FixLinterCore {
    public static String fixLintVariable(String name) {
        // Upper case all word before (_)
        char[] chars = name.toCharArray();
        for (int i = 1; i < name.length(); i++) {
            if (chars[i - 1] == '_' && chars[i] != '_') {
                chars[i] = Character.toUpperCase(chars[i]);
            }
        }
        // Remove all word (_)
        return String.valueOf(chars).replaceAll("_", "");
    }

    public static String fixLintsForVariableInLine(String line, String variable) {
        final String fixed = fixLintVariable(variable);
        return line.replaceAll(variable, fixed);
    }
}
