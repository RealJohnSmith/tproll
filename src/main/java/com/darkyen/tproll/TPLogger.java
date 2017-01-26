package com.darkyen.tproll;

import com.darkyen.tproll.util.LevelChangeListener;
import com.darkyen.tproll.util.TimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.ArrayList;

import static com.darkyen.tproll.util.PrettyPrinter.append;

/**
 * Lightweight, GC friendly and thread-safe logger implementation.
 *
 * Default log level is INFO, default time provider is {@link TimeProvider#CURRENT_TIME_PROVIDER},
 * default level change listener is {@link LevelChangeListener#LOG} and
 * default log function is {@link LogFunction#SIMPLE_LOG_FUNCTION}.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class TPLogger implements Logger {

    //region Non-static
    private final String name;

    public TPLogger(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    //endregion

    public static final byte TRACE = 1;
    public static final byte DEBUG = 2;
    public static final byte INFO = 3;
    public static final byte WARN = 4;
    public static final byte ERROR = 5;
    /** Special level which always gets through, used for logging-related messages. */
    public static final byte LOG = 6;

    private static LogFunction logFunction = LogFunction.SIMPLE_LOG_FUNCTION;
    private static LevelChangeListener levelChangeListener = LevelChangeListener.LOG;
    private static TimeProvider timeProvider = TimeProvider.CURRENT_TIME_PROVIDER;

    private static byte logLevel = INFO;
    private static boolean trace = false;
    private static boolean debug = false;
    private static boolean info = true;
    private static boolean warn = true;
    private static boolean error = true;

    public static String levelName(byte logLevel){
        switch (logLevel) {
            case TRACE: return "TRACE";
            case DEBUG: return "DEBUG";
            case INFO: return "INFO";
            case WARN: return "WARN";
            case ERROR: return "ERROR";
            case LOG: return "LOG";
            default: return "UNKNOWN LEVEL "+logLevel;
        }
    }

    public static void TRACE() {
        logLevel = TRACE;
        trace = debug = info = warn = error = true;
        levelChangeListener.levelChanged(TRACE);
    }

    public static void DEBUG() {
        logLevel = DEBUG;
        trace = false;
        debug = info = warn = error = true;
        levelChangeListener.levelChanged(DEBUG);
    }

    public static void INFO() {
        logLevel = INFO;
        trace = debug = false;
        info = warn = error = true;
        levelChangeListener.levelChanged(INFO);
    }

    public static void WARN() {
        logLevel = WARN;
        trace = debug = info = false;
        warn = error = true;
        levelChangeListener.levelChanged(WARN);
    }

    public static void ERROR() {
        logLevel = ERROR;
        trace = debug = info = warn = false;
        error = true;
        levelChangeListener.levelChanged(ERROR);
    }

    public static byte getLogLevel(){
        return logLevel;
    }

    public static void setLogFunction(LogFunction logFunction) {
        if (logFunction == null) throw new NullPointerException("logFunction may not be null");
        TPLogger.logFunction = logFunction;
    }

    public static LogFunction getLogFunction() {
        return logFunction;
    }

    public static void setLevelChangeListener(LevelChangeListener levelChangeListener) {
        if (levelChangeListener == null) throw new NullPointerException("levelChangeListener may not be null");
        TPLogger.levelChangeListener = levelChangeListener;
    }

    public static LevelChangeListener getLevelChangeListener() {
        return levelChangeListener;
    }

    public static void setTimeProvider(TimeProvider timeProvider) {
        if (timeProvider == null) throw new NullPointerException("timeProvider may not be null");
        TPLogger.timeProvider = timeProvider;
    }

    public static TimeProvider getTimeProvider() {
        return timeProvider;
    }

    //region isEnabled
    @Override
    public boolean isTraceEnabled() {
        return trace;
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return trace;
    }

    @Override
    public boolean isDebugEnabled() {
        return debug;
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return debug;
    }

    @Override
    public boolean isInfoEnabled() {
        return info;
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return info;
    }

    @Override
    public boolean isWarnEnabled() {
        return warn;
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return warn;
    }

    @Override
    public boolean isErrorEnabled() {
        return error;
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return error;
    }
    //endregion

    //region Trace
    @Override
    public void trace(String msg) {
        if (trace) log(TRACE, null, msg);
    }

    @Override
    public void trace(String format, Object arg) {
        if (trace) log(TRACE, null, format, arg);
    }

    @Override
    public void trace(String format, Object argA, Object argB) {
        if (trace) log(TRACE, null, format, argA, argB);
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (trace) log(TRACE, null, format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (trace) log(TRACE, null, msg, t);
    }

    @Override
    public void trace(Marker marker, String msg) {
        if (trace) log(TRACE, marker, msg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        if (trace) log(TRACE, marker, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        if (trace) log(TRACE, marker, format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        if (trace) log(TRACE, marker, format, argArray);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        if (trace) log(TRACE, marker, msg, t);
    }
    //endregion

    //region Debug
    @Override
    public void debug(String msg) {
        if (debug) log(DEBUG, null, msg);
    }

    @Override
    public void debug(String format, Object arg) {
        if (debug) log(DEBUG, null, format, arg);
    }

    @Override
    public void debug(String format, Object argA, Object argB) {
        if (debug) log(DEBUG, null, format, argA, argB);
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (debug) log(DEBUG, null, format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        if (debug) log(DEBUG, null, msg, t);
    }


    @Override
    public void debug(Marker marker, String msg) {
        if (debug) log(DEBUG, marker, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        if (debug) log(DEBUG, marker, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        if (debug) log(DEBUG, marker, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        if (debug) log(DEBUG, marker, format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        if (debug) log(DEBUG, marker, msg, t);
    }
    //endregion

    //region Info
    @Override
    public void info(String msg) {
        if (info) log(INFO, null, msg);
    }

    @Override
    public void info(String format, Object arg) {
        if (info) log(INFO, null, format, arg);
    }

    @Override
    public void info(String format, Object argA, Object argB) {
        if (info) log(INFO, null, format, argA, argB);
    }

    @Override
    public void info(String format, Object... arguments) {
        if (info) log(INFO, null, format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        if (info) log(INFO, null, msg, t);
    }

    @Override
    public void info(Marker marker, String msg) {
        if (info) log(INFO, marker, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        if (info) log(INFO, marker, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        if (info) log(INFO, marker, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        if (info) log(INFO, marker, format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        if (info) log(INFO, marker, msg, t);
    }
    //endregion

    //region Warn
    @Override
    public void warn(String msg) {
        if (warn) log(WARN, null, msg);
    }

    @Override
    public void warn(String format, Object arg) {
        if (warn) log(WARN, null, format, arg);
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (warn) log(WARN, null, format, arguments);
    }

    @Override
    public void warn(String format, Object argA, Object argB) {
        if (warn) log(WARN, null, format, argA, argB);
    }

    @Override
    public void warn(String msg, Throwable t) {
        if (warn) log(WARN, null, msg, t);
    }

    @Override
    public void warn(Marker marker, String msg) {
        if (warn) log(WARN, marker, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        if (warn) log(WARN, marker, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        if (warn) log(WARN, marker, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        if (warn) log(WARN, marker, format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        if (warn) log(WARN, marker, msg, t);
    }
    //endregion

    //region Error
    @Override
    public void error(String msg) {
        if (error) log(ERROR, null, msg);
    }

    @Override
    public void error(String format, Object arg) {
        if (error) log(ERROR, null, format, arg);
    }

    @Override
    public void error(String format, Object argA, Object argB) {
        if (error) log(ERROR, null, format, argA, argB);
    }

    @Override
    public void error(String format, Object... arguments) {
        if (error) log(ERROR, null, format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        if (error) log(ERROR, null, msg, t);
    }

    @Override
    public void error(Marker marker, String msg) {
        if (error) log(ERROR, marker, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        if (error) log(ERROR, marker, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        if (error) log(ERROR, marker, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        if (error) log(ERROR, marker, format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        if (error) log(ERROR, marker, msg, t);
    }
    //endregion

    //region Log
    public void log(String msg) {
        log(LOG, null, msg);
    }

    public void log(String format, Object arg) {
        log(LOG, null, format, arg);
    }

    public void log(String format, Object argA, Object argB) {
        log(LOG, null, format, argA, argB);
    }

    public void log(String format, Object... arguments) {
        log(LOG, null, format, arguments);
    }

    public void log(String msg, Throwable t) {
        log(LOG, null, msg, t);
    }
    //endregion

    //------------------------------------- INTERNAL ----------------------------------------------------

    private final ArrayList<Object> arguments = new ArrayList<>();

    private void log(byte level, Marker marker, String msg) {
        synchronized (arguments) {
            doLog(level, marker, msg);
        }
    }

    private void log(byte level, Marker marker, String format, Object arg) {
        synchronized (arguments) {
            arguments.add(arg);
            doLog(level, marker, format);
        }
    }

    private void log(byte level, Marker marker, String format, Object argA, Object argB) {
        synchronized (arguments) {
            arguments.add(argA);
            arguments.add(argB);
            doLog(level, marker, format);
        }
    }

    private void log(byte level, Marker marker, String format, Object... arguments) {
        synchronized (this.arguments) {
            this.arguments.ensureCapacity(arguments.length);
            //noinspection ManualArrayToCollectionCopy
            for (Object argument : arguments) {
                this.arguments.add(argument);
            }
            doLog(level, marker, format);
        }
    }

    private final StringBuilder sb = new StringBuilder(64);

    private void doLog(byte level, Marker marker, String message) {
        final ArrayList<Object> objects = this.arguments;
        final StringBuilder sb = this.sb;
        final LogFunction logFunction = TPLogger.logFunction;

        final long time = timeProvider.timeMillis();

        if (objects.isEmpty()) {
            sb.append(message);
            logFunction.log(name, time, level, marker, sb, null);
        } else {
            boolean escaping = false;
            boolean substituting = false;
            int substitutingIndex = 0;
            Throwable throwable = null;

            for (int i = 0, l = message.length(); i < l; i++) {
                final char c = message.charAt(i);
                if (substituting) {
                    substituting = false;
                    if (c == '}') {
                        if (substitutingIndex != objects.size()) {
                            final Object item = objects.get(substitutingIndex);
                            if (item instanceof Throwable) {
                                throwable = (Throwable) item;
                            }
                            append(sb, item);
                            substitutingIndex++;
                        } else {
                            sb.append("{}");
                        }
                        continue;
                    } else {
                        sb.append('{');
                    }
                }

                if (c == '\\') {
                    if (escaping) {
                        sb.append('\\');
                    } else {
                        escaping = true;
                    }
                } else if (c == '{') {
                    if (escaping) {
                        escaping = false;
                        sb.append('{');
                    } else {
                        substituting = true;
                    }
                } else {
                    sb.append(c);
                }
            }
            //There are items that were not appended yet, because they have no {}
            //It could be just one throwable, in that case do not substitute it in
            if(substitutingIndex == objects.size() - 1 && objects.get(substitutingIndex) instanceof Throwable){
                throwable = (Throwable) objects.get(substitutingIndex);
            } else if (substitutingIndex < objects.size()) {
                //It is not one throwable. It could be more things ended with throwable though
                sb.append(" {");
                do{
                    final Object item = objects.get(substitutingIndex);
                    append:{
                        if (item instanceof Throwable) {
                            throwable = (Throwable) item;
                            if(substitutingIndex == objects.size() - 1) {
                                //When throwable is last in list and not in info string, don't print it.
                                //It is guaranteed that it will be printed by trace.
                                break append;
                            }
                        }
                        append(sb, item);
                    }
                    substitutingIndex++;

                    sb.append(", ");
                }while(substitutingIndex < objects.size());
                sb.setLength(sb.length() - 2);
                sb.append('}');
            }
            objects.clear();
            logFunction.log(name, time, level, marker, sb, throwable);
        }
        sb.setLength(0);
    }

    /** Will call {@link Thread#setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler)}
     * with a function that logs these exceptions. If there already is a handler, it is called after the exception is logged. */
    public static void attachUnhandledExceptionLogger(){
        final Logger logger = LoggerFactory.getLogger("UnhandledException");
        final Thread.UncaughtExceptionHandler originalHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            logger.error("{} has crashed with exception: {}",t, e);
            if(originalHandler != null){
                originalHandler.uncaughtException(t, e);
            }
        });
    }
}

