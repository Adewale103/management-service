package com.payu.management_service.utils;


import static com.payu.management_service.utils.Constants.APPENDABLE_SEPARATOR;

public class AppendableReferenceUtils {

    private static String[] getParts(String reference) {
        if (reference == null || !reference.contains(APPENDABLE_SEPARATOR)) {
            return null;
        }
        String[] parts = reference.split(APPENDABLE_SEPARATOR);
        if (parts.length != 2) return null;
        return parts;
    }

    public static Long getIdFrom(String reference) {
        try {
            return Long.parseLong(getParts(reference)[0]);
        } catch (Exception ignored) {
        }
        return 0L;
    }

    public static String getReferenceFrom(String reference) {
        return getParts(reference)[1];
    }
}