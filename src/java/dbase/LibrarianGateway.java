package dbase;

import dto.LibrarianDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gdm1
 */
public class LibrarianGateway
{

    private static final String LIBRARIAN_WITH_USERNAME = "SELECT * FROM Librarian WHERE username = ?";

    public LibrarianDTO findLibrarianByUsername(String username)
    {
        LibrarianDTO librarian = null;
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(LIBRARIAN_WITH_USERNAME);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next())
            {
                librarian = new LibrarianDTO(rs.getInt("ID"),
                                       rs.getString("LIBRARIANNAME"),
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
        return librarian;
    }
}
