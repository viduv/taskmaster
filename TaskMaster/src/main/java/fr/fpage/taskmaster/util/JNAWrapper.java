package fr.fpage.taskmaster.util;

import com.sun.jna.Library;

public interface JNAWrapper extends Library {
    int kill(int pid, int signal);
    int umask(int umask);
}
