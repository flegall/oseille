package lobstre.oseille.parser;

import java.io.File;
import java.io.IOException;

import lobstre.oseille.model.Account;
import lobstre.oseille.parser.Parser;

public class ParserTest {
    public static void main (String[] args) throws IOException {
        Account account = Parser.read (new File ("test/test.txt"));
        account.toString ();
        Parser.write (account, new File ("test/test-output.txt"));
        Account account2 = Parser.read (new File ("test/test-output.txt"));
        account2.toString ();
    }
}
