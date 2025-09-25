package g62588.dev3.oxono.model.commands;

import g62588.dev3.oxono.model.GameState;

public interface Command {
    void execute();
    void unexecute();
}
