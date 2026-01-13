package com.jozias.product.catalog.domain.util;

import static java.util.Objects.isNull;

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isAValidString(String str) {
        return isAValidString(str, 1);
    }

    public static boolean isAValidString(String str, int minLength) {
        if (isNull(str)) {
            return false;
        }

        String trimmed = str.trim();
        if (trimmed.isEmpty()) {
            return false;
        }

        int lengthWithoutSpaces = trimmed.replaceAll("\\s+", "").length();
        return lengthWithoutSpaces >= minLength;
    }
}
