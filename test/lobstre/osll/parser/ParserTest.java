package lobstre.osll.parser;

import java.io.File;
import java.io.IOException;

import lobstre.osll.model.Account;

public class ParserTest {
    public static void main (String[] args) throws IOException {
        Account account = Parser.read (new File ("test/test.txt"));
        account.toString ();
        Parser.write (account, new File ("test/test-output.txt"));
        Account account2 = Parser.read (new File ("test/test-output.txt"));
        account2.toString ();
    }
}
