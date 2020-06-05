package dbase;

import dto.BookDTO;
import dto.CopyDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author gdm1
 */
public class BookGateway
{

    private static final String ALL_BOOKS = "SELECT * FROM Book";
    
    private static final String COPIES_FOR_BOOK 
            = "SELECT Copy.Id AS CopyId, ReferenceOnly, Loan.id AS LoanId "
            + "FROM Copy JOIN Book ON Copy.BookId = Book.Id "
            + "LEFT JOIN Loan ON Copy.id = Loan.CopyId "
            + "WHERE Book.Id = ? "
            + "ORDER BY Copy.Id";
    
    private static final String CREATE_COPIES_FOR_BOOK
            = "INSERT INTO Copy (BookId, ReferenceOnly) "
            + "VALUES (?, ?)";

    public void addCopiesToBook(BookDTO book)
    {
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(COPIES_FOR_BOOK);
            stmt.setInt(1, book.getId());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next())
            {
                CopyDTO copy = new CopyDTO(rs.getInt("COPYID"),
                                           book,
                                           rs.getBoolean("REFERENCEONLY"),
                                           rs.getInt("LOANID") != 0);
                book.addCopy(copy);
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
    }
    
    public void createCopies(int bookId, int numCopies, boolean referenceOnly)
    {
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(CREATE_COPIES_FOR_BOOK);
            stmt.setInt(1, bookId);
            stmt.setBoolean(2, referenceOnly);
            
            for (int i = 0; i < numCopies; i++)
            {
                stmt.executeUpdate();
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
    }

    public ArrayList<BookDTO> findAllBooks()
    {
        ArrayList<BookDTO> list = new ArrayList<>();
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(ALL_BOOKS);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next())
            {
                BookDTO book = new BookDTO(rs.getInt("ID"),
                                       rs.getString("TITLE"),
                                       rs.getString("AUTHOR"),
                                       rs.getString("ISBN"));
                list.add(book);
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
        return list;
    }
}
