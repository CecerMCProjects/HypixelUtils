package com.cecer1.modframework.common.utils;

import net.minecraft.client.Minecraft;

import java.io.InputStream;
import java.util.Scanner;

public class MiscUtils {

    public static String getWholeInputStreamAsString(InputStream stream) {
        Scanner scanner = new Scanner(stream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
