package dbase;

import dto.BookDTO;
import dto.CopyDTO;
import dto.LoanDTO;
import dto.MemberDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author gdm1
 */
public class LoanGateway
{

    private static final String ARCHIVE_LOAN
            = "INSERT INTO LoanHistory (MemberId, CopyId, LoanDate, DueDate, ReturnDate, NumRenewals) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String BORROW_BOOK
            = "INSERT INTO Loan (MemberId, CopyId, LoanDate, DueDate, NumRenewals) "
            + "VALUES (?, ?, ?, ?, 0)";

    private static final String CURRENT_LOANS_FOR_MEMBER
            = "SELECT Loan.Id AS LoanId, LoanDate, DueDate, NumRenewals, "
            + "Copy.Id AS CopyId, ReferenceOnly, "
            + "Book.id AS BookId, title, author, isbn "
            + "FROM Loan JOIN Copy ON Loan.CopyId = Copy.Id "
            + "JOIN Book ON Copy.BookId = Book.Id "
            + "WHERE Loan.memberId = ?";
    
    public static final String DELETE_LOAN
            = "DELETE FROM Loan "
            + "WHERE id = ?";
    
    private static final String LOAN_HISTORY_FOR_MEMBER
            = "SELECT LoanHistory.Id AS LoanId, LoanDate, DueDate, ReturnDate, NumRenewals, "
            + "Copy.Id AS CopyId, ReferenceOnly, "
            + "Book.id AS BookId, title, author, isbn "
            + "FROM LoanHistory JOIN Copy ON LoanHistory.CopyId = Copy.Id "
            + "JOIN Book ON Copy.BookId = Book.Id "
            + "WHERE LoanHistory.memberId = ?";
    
    private static final String RENEW_LOAN
            = "UPDATE Loan "
            + "SET NumRenewals = ? "
            + "WHERE Id = ?";

    public boolean archiveLoan(int memberId, int copyId, Calendar loanDate, Calendar dueDate, Calendar returnDate, int numRenewals)
    {
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(ARCHIVE_LOAN);
            stmt.setInt(1, memberId);
            stmt.setInt(2, copyId);
            stmt.setDate(3, new Date(loanDate.getTimeInMillis()));
            stmt.setDate(4, new Date(dueDate.getTimeInMillis()));
            stmt.setDate(5, new Date(returnDate.getTimeInMillis()));
            stmt.setInt(6, numRenewals);
            int rows = stmt.executeUpdate();

            con.close();
            return rows == 1;
        }
        catch (NullPointerException npe)
        {
            System.err.println("No connection available");
        }
        catch (SQLException sqle)
        {
            System.err.println(sqle.getMessage());
            sqle.printStackTrace();
        }
        return false;
    }

    public boolean borrowBook(int memberId, int copyId, Calendar loanDate, Calendar dueDate)
    {
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(BORROW_BOOK);
            stmt.setInt(1, memberId);
            stmt.setInt(2, copyId);
            stmt.setDate(3, new Date(loanDate.getTimeInMillis()));
            stmt.setDate(4, new Date(dueDate.getTimeInMillis()));
            int rows = stmt.executeUpdate();

            con.close();
            return rows == 1;
        }
        catch (NullPointerException npe)
        {
            System.err.println("No connection available");
        }
        catch (SQLException sqle)
        {
            System.err.println(sqle.getMessage());
        }
        return false;
    }

    public boolean deleteLoan(int loanId)
    {
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(DELETE_LOAN);
            stmt.setInt(1, loanId);
            int rows = stmt.executeUpdate();

            con.close();
            return rows == 1;
        }
        catch (NullPointerException npe)
        {
            System.err.println("No connection available");
        }
        catch (SQLException sqle)
        {
            System.err.println(sqle.getMessage());
        }
        return false;
    }

    public ArrayList<LoanDTO> findCurrentLoansForMember(MemberDTO member)
    {
        ArrayList<LoanDTO> list = new ArrayList<>();
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(CURRENT_LOANS_FOR_MEMBER);
            stmt.setInt(1, member.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                BookDTO book = new BookDTO(rs.getInt("BOOKID"), rs.getString("TITLE"), rs.getString("AUTHOR"), rs.getString("ISBN"));

                CopyDTO copy = new CopyDTO(rs.getInt("COPYID"), book, rs.getBoolean("REFERENCEONLY"), true);

                Calendar loanDate = Calendar.getInstance();
                loanDate.setTime(rs.getDate("LOANDATE"));
                
                Calendar dueDate = Calendar.getInstance();
                dueDate.setTime(rs.getDate("DUEDATE"));
                
                LoanDTO loan = new LoanDTO(
                        rs.getInt("LOANID"),
                        member,
                        copy,
                        loanDate,
                        dueDate,
                        null,
                        rs.getInt("NUMRENEWALS")
                );

                list.add(loan);
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
    
    public ArrayList<LoanDTO> getLoanHistoryForMember(int memberId)
    {
        ArrayList<LoanDTO> list = new ArrayList<>();
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(LOAN_HISTORY_FOR_MEMBER);
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                BookDTO book = new BookDTO(rs.getInt("BOOKID"), rs.getString("TITLE"), rs.getString("AUTHOR"), rs.getString("ISBN"));

                CopyDTO copy = new CopyDTO(rs.getInt("COPYID"), book, rs.getBoolean("REFERENCEONLY"), false);

                Calendar loanDate = Calendar.getInstance();
                loanDate.setTime(rs.getDate("LOANDATE"));
                
                Calendar dueDate = Calendar.getInstance();
                dueDate.setTime(rs.getDate("DUEDATE"));
                
                Calendar returnDate = Calendar.getInstance();
                returnDate.setTime(rs.getDate("RETURNDATE"));
                
                LoanDTO loan = new LoanDTO(
                        rs.getInt("LOANID"),
                        null,
                        copy,
                        loanDate,
                        dueDate,
                        returnDate,
                        rs.getInt("NUMRENEWALS")
                );

                list.add(loan);
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
    
    public boolean renewLoan(LoanDTO loan)
    {
        Connection con = DB_Manager.getConnection();
        try
        {
            PreparedStatement stmt = con.prepareStatement(RENEW_LOAN);
            stmt.setInt(1, loan.getNumberOfRenewals()+1);
            stmt.setInt(2, loan.getId());
            int rows = stmt.executeUpdate();

            con.close();
            return rows == 1;
        }
        catch (NullPointerException npe)
        {
            System.err.println("No connection available");
        }
        catch (SQLException sqle)
        {
            System.err.println(sqle.getMessage());
        }
        return false;
    }
}
