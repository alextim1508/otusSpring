package com.alextim.shell;

import com.alextim.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@RequiredArgsConstructor
@ShellComponent
public class ShellCommands {

    private final InterviewService service;

    //shell:>interview --name ivan --amount-questions 5
    //shell:>exit
    @ShellMethod("interview")
    public void interview(@ShellOption String name, @ShellOption int amountQuestions) {
        service.interview();
    }
}
