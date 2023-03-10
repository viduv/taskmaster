package fr.fpage.taskmaster.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;

@Getter
@NoArgsConstructor
@ToString
public class ProcessConfiguration {

    private String name;
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
