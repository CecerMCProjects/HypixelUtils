package com.cecer1.hypixelutils.utils;

import java.io.InputStream;
import java.util.Scanner;

public class MiscUtils {

    public static String getWholeInputStreamAsString(InputStream stream) {
        Scanner scanner = new Scanner(stream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    public static String joinStringArray(String[] s, String glue)
    {
        int k = s.length;
        if ( k == 0 )
            return null;
        
        StringBuilder out = new StringBuilder();
        out.append(s[0]);
        for (int x=1; x < k; ++x)
        {
            out.append(glue).append(s[x]);
        }
        return out.toString();
    }
}
