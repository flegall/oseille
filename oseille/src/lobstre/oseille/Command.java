package lobstre.oseille;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

public interface Command {
    void accepts (List<String> arguments, Collection<String> errors, String fileName);
    void execute (String fileName, List<String> arguments) throws IOException, ParseException;
}
