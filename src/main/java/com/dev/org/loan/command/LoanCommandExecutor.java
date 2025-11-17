package com.dev.org.loan.command;

import org.springframework.stereotype.Component;

/**
 * The invoker in the Command Pattern.
 * It is responsible for executing a given command.
 */
@Component
public class LoanCommandExecutor {
    public void executeCommand(LoanCommand command) {
        command.execute();
    }
}
