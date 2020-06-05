package command;

import dto.MemberDTO;
import handler.MemberManager;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author gdm1
 */
public class CheckMemberCredentialsCommand implements Command
{

    private final String username;
    private String pwd;
    private final MemberManager manager;

    public CheckMemberCredentialsCommand(String username, String pwd)
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

        this.manager = new MemberManager();
    }

    @Override
    public Object execute()
    {
        MemberDTO member = manager.findMemberByUsername(username);
        if (member != null && member.passwordMatches(pwd))
        {
            return member;
        }
        else
        {
            return null;
        }
    }
}
