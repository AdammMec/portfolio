package g62588.dev3.oxono.model.commands;

import g62588.dev3.oxono.model.event.Event;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    public void execute(Command command){
        command.execute();
        undoStack.add(command);
        redoStack.clear();
    }

    public void add(Command command){
        undoStack.add(command);
        redoStack.clear();
    }

    public void print(Stack<Command> s){
        System.out.println("---");
        for (int i = 0; i < s.size(); i++) {
            System.out.println(s.get(i).toString());
        }
        System.out.println("---");
    }
    public void undo(){
        if(!undoStack.isEmpty()){
            Command last = undoStack.getLast();
            last.unexecute();
            redoStack.add(last);
            undoStack.pop();
        }
    }

    public void redo(){
        if (!redoStack.isEmpty()){
            Command last = redoStack.getLast();
            last.execute();
            undoStack.add(last);
            redoStack.pop();
        }

    }
}
