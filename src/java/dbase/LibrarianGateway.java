package dbase;

import dto.LibrarianDTO;
import dto.MemberDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gdm1
 */
public class LibrarianGateway {

    private static final String LIBRARIAN_WITH_USERNAME = "SELECT * FROM Librarian WHERE username = ?";
    private static final String MEMBER_WITH_ID = "SELECT * FROM Member WHERE id = ?";

    public LibrarianDTO findLibrarianByUsername(String username) {
        LibrarianDTO librarian = null;
        Connection con = DB_Manager.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(LIBRARIAN_WITH_USERNAME);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                librarian = new LibrarianDTO(rs.getInt("ID"),
                        rs.getString("LIBRARIANNAME"),
                        rs.getString("USERNAME"),
                        rs.getString("PWD"));
            }

            con.close();
        } catch (NullPointerException npe) {
            System.err.println("No connection available");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return librarian;
    }

    public MemberDTO findMemberByUserId(int userId) {
        MemberDTO member = null;
        Connection con = DB_Manager.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement(MEMBER_WITH_ID);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                member = new MemberDTO(rs.getInt("ID"),
                        rs.getString("MEMBERNAME"),
                        rs.getString("USERNAME"),
                        rs.getString("PWD"));
            }

            con.close();
        } catch (NullPointerException npe) {
            System.err.println("No connection available");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return member;
    }
}
