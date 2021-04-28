package com.alextim.shell;

import com.alextim.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class ShellCommands {

    private final InterviewService service;

    @ShellMethod("interview")
    public void interview() {
        service.interview();
    }
}

