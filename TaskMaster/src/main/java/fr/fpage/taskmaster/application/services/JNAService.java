package fr.fpage.taskmaster.application.services;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import fr.fpage.taskmaster.model.ExitSignalType;
import fr.fpage.taskmaster.util.JNAWrapper;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class JNAService {

    private final JNAWrapper jnaWrapper = Native.load((Platform.isWindows() ? "msvcrt" : "c"), JNAWrapper.class);

    public void kill(java.lang.Process process, ExitSignalType signalType) {
        Logger.getGlobal().log(Level.INFO, "Send " + signalType + " to program " + process.pid());
        Logger.getGlobal().log(Level.INFO, "Result " + this.jnaWrapper.kill((int) process.pid(), signalType.getSig()));

    }

}
