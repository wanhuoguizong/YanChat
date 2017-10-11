package com.xinlingyijiu.yanchat.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by laotou on 2017/10/11.
 */
public class IOUtil {
    public static void close(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
