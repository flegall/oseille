package lobstre.oseille.parser;

import lobstre.oseille.model.Account;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    @Test
    public void test () throws IOException {
        Account account = Parser.read (new File ("test-files/test.txt"));
        assertEquals(account.getInitialAmount(), new BigDecimal("300.0"));
        
        Parser.write (account, new File ("test-files/test-output.txt"));
        Account account2 = Parser.read (new File ("test-files/test-output.txt"));
        
        assertEquals(account.getBudgets(), account2.getBudgets());
        assertEquals(account.getInitialAmount(), account2.getInitialAmount());
    }
}
