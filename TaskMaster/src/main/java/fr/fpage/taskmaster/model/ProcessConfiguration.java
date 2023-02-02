package fr.fpage.taskmaster.model;

import java.util.HashMap;

public class ProcessConfiguration {

    private String cmd;
    private int nbInstance;
    private boolean startAtLaunch;
    private RestartType restartType;
    private int expectedExitCode;
    private int startupTime;
    private int restartRetryCount;
    private ExitSignalType exitSignal;
    private int gracefulStopTime;
    private LogType log;
    private HashMap<String, String> env;
    private String folder;
    private String umask;
}
