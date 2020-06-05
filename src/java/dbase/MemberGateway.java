package dbase;

import dto.MemberDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gdm1
 */
public class MemberGateway
{

    private static final String MEMBER_WITH_USERNAME = "SELECT * FROM Member WHERE username = ?";

    public MemberDTO findMemberByUsername(String username)
    {
        MemberDTO member = null;
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(MEMBER_WITH_USERNAME);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next())
            {
                member = new MemberDTO(rs.getInt("ID"),
                                       rs.getString("MEMBERNAME"),
                                       rs.getString("USERNAME"),
                                       rs.getString("PWD"));
            }
            
            con.close();
        }
        catch (NullPointerException npe)
        {
            System.err.println("No connection available");
        }
        catch (SQLException sqle)
        {
            System.err.println(sqle.getMessage());
        }
        return member;
    }
}
