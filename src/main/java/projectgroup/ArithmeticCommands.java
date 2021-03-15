package projectgroup;

import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
public class ArithmeticCommands {

    @ShellMethod("Add two integers together.")
    public int add(int a, int b) {
        return a + b;
    }

    @ShellMethod("Subtract two integers together.")
    public int sub(int a, int b) {
        return a - b;
    }

}

