package fr.fpage.taskmaster.model;

import jdk.jfr.SettingDefinition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProcessConfiguration {

    private String name;
    private String cmd;
    private int nbInstance;
    private boolean startAtLaunch = true;
    private RestartType restartType = RestartType.NEVER;
    private int expectedExitCode;
    private int startupTime;
    private int restartRetryCount;
    private ExitSignalType exitSignal;
    private long gracefulStopTime = 5;
    private HashMap<String, String> env = new HashMap<>();
    private String folder;
    private String umask;
    private String stdoutFile;
    private String stderrFile;

}
