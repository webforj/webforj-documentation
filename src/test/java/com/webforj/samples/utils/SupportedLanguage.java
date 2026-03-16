package com.webforj.samples.utils;

public enum SupportedLanguage {
    JAVA,
    KOTLIN;

    public String getPath(String path) {
        if (this == KOTLIN) {
            return path + "kotlin";
        }
        return path;
    }

}
