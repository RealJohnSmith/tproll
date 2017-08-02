package com.darkyen.tproll.integration;

import com.darkyen.tproll.TPLogger;
import com.darkyen.tproll.util.LevelChangeListener;
import com.darkyen.tproll.util.SimpleMarker;
import com.esotericsoftware.minlog.Log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static com.darkyen.tproll.TPLogger.*;

/**
 * Call {@link #enable()} if you want to route java.util.logging logs through tproll.
 */
public class JavaLoggingIntegration {

    private JavaLoggingIntegration() {
    }

    public static final SimpleMarker JAVA_LOGGING_MARKER = new SimpleMarker() {
        @Override
        public String getName() {
            return "java.util.logging";
        }
    };
    public static final TPLogger LOGGER = new TPLogger("java.util.logging");

    public static byte toTPLevel(Level level) {
        final int levelNum = level.intValue();
        if (levelNum < Level.FINE.intValue()) {
            return TPLogger.TRACE;
        } else if (levelNum < Level.INFO.intValue()) {
            return TPLogger.DEBUG;
        } else if (levelNum < Level.WARNING.intValue()) {
            return INFO;
        } else if (levelNum < Level.SEVERE.intValue()) {
            return WARN;
        } else {
            return ERROR;
        }
    }

    public static Level fromTPLevel(byte level) {
        switch (level) {
            case TRACE:
                return Level.FINER;
            case DEBUG:
                return Level.FINE;
            case INFO:
                return Level.INFO;
            case WARN:
                return Level.WARNING;
            case ERROR:
                return Level.SEVERE;
            case LOG:
                return Level.CONFIG;
        }
        if (level < TRACE) {
            return Level.FINER;
        } else {
            return Level.SEVERE;
        }
    }

    /** Calls {@link Log#setLogger} with a logger which uses logger of {@link TPLogger} for logging.
     * Also sets */
    public static void enable() {
        final Logger globalLogger = Logger.getGlobal();
        for (Handler handler : globalLogger.getHandlers()) {
            globalLogger.removeHandler(handler);
        }
        globalLogger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                LOGGER.logCustom(record.getLoggerName(), record.getMillis(), toTPLevel(record.getLevel()), JAVA_LOGGING_MARKER, record.getMessage(), record.getParameters(), record.getThrown());
            }

            @Override
            public void flush() {
            }

            @Override
            public void close() throws SecurityException {
            }
        });

        globalLogger.setLevel(fromTPLevel(TPLogger.getLogLevel()));
        final LevelChangeListener oldChangeListener = TPLogger.getLevelChangeListener();
        TPLogger.setLevelChangeListener(to -> {
            oldChangeListener.levelChanged(to);
            globalLogger.setLevel(fromTPLevel(to));
        });
    }
}