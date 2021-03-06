package lobstre.oseille.commands;

import lobstre.oseille.Command;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class NextSimulate implements Command {

    @Override
    public void accepts(List<String> arguments, Collection<String> errors, String fileName) {
        NextCommit.accepts(arguments, errors, fileName, "next-simulate");
    }

    @Override
    public void execute(String fileName, List<String> arguments) throws IOException {
        NextCommit.next(fileName, arguments, false);
    }

}
