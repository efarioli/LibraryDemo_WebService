package command;

import dto.LibrarianDTO;
import handler.LibrarianManager;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author gdm1
 */
public class CheckLibrarianCredentialsCommand implements Command
{

    private final String username;
    private String pwd;
    private final LibrarianManager manager;

    public CheckLibrarianCredentialsCommand(String username, String pwd)
    {
        this.username = username;

        try
        {
            byte[] hash = MessageDigest
                    .getInstance("SHA-256")
                    .digest(pwd.getBytes(StandardCharsets.UTF_8));

            this.pwd = Base64.getEncoder().encodeToString(hash);
        }
        catch (NoSuchAlgorithmException e)
        {
            this.pwd = "";
        }

        this.manager = new LibrarianManager();
    }

    @Override
    public Object execute()
    {
        LibrarianDTO librarian = manager.findLibrarianByUsername(username);
        if (librarian != null && librarian.passwordMatches(pwd))
        {
            return librarian;
        }
        else
        {
            return null;
        }
    }
}
