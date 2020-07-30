/*
 * Copyright (c) CP4J Project
 */

package com.vb.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Log {
    private static boolean debug;
    private static final Logger logger = Logger.getLogger("DebugLog");

    public static void setDebug(boolean debug) {
        Log.debug = debug;
    }

    public static void d(String message, Object... params) {
        if (debug) {
            logger.log(Level.INFO, message, params);
        }
    }
}
