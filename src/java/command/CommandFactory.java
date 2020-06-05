package command;

import dto.BookDTO;
import dto.LoanDTO;
import dto.MemberDTO;

/**
 *
 * @author gdm1
 */
public class CommandFactory
{
    public static final int ADD_COPIES_TO_BOOK = 1;
    public static final int ALL_BOOKS = 2;
    public static final int BORROW_BOOK = 3;
    public static final int CHECK_MEMBER_CREDENTIALS = 4;
    public static final int CHECK_LIBRARIAN_CREDENTIALS = 5;
    public static final int CREATE_COPIES_FOR_BOOK = 6;
    public static final int CURRENT_LOANS_FOR_MEMBER = 7;
    public static final int LOAN_HISTORY_FOR_MEMBER = 8;
    public static final int RENEW_LOAN = 9;
    public static final int RETURN_BOOK = 10;
    
    public static Command createCommand(int commandType)
    {
        switch (commandType)
        {
            case ALL_BOOKS:
                return new GetAllBooksCommand();
            default:
                return new NullCommand();
        }
    }
    
    public static Command createCommand(int commandType, int id)
    {
        switch (commandType)
        {
            case LOAN_HISTORY_FOR_MEMBER:
                return new GetLoanHistoryCommand(id);
            default:
                return new NullCommand();
        }
    }
    
    public static Command createCommand(int commandType, Object value)
    {
        switch (commandType)
        {
            case ADD_COPIES_TO_BOOK:
                return new AddCopiesToBookCommand((BookDTO)value);
            case CURRENT_LOANS_FOR_MEMBER:
                return new GetCurrentLoansCommand((MemberDTO)value);
            case RENEW_LOAN:
                return new RenewLoanCommand((LoanDTO)value);
            case RETURN_BOOK:
                return new ReturnBookCommand((LoanDTO)value);
            default:
                return new NullCommand();
        }
    }
    
    public static Command createCommand(int commandType, String username, String password)
    {
        switch (commandType)
        {
            case CHECK_MEMBER_CREDENTIALS:
                return new CheckMemberCredentialsCommand(username, password);
            case CHECK_LIBRARIAN_CREDENTIALS:
                return new CheckLibrarianCredentialsCommand(username, password);
            default:
                return new NullCommand();
        }
    }
    
    public static Command createCommand(int commandType, int memberId, int copyId)
    {
        switch (commandType)
        {
            case BORROW_BOOK:
                return new BorrowBookCommand(memberId, copyId);
            default:
                return new NullCommand();
        }
    }
    
    public static Command createCommand(int commandType, int bookId, int numCopies, boolean refOnly)
    {
        switch (commandType)
        {
            case CREATE_COPIES_FOR_BOOK:
                return new CreateCopiesForBookCommand(bookId, numCopies, refOnly);
            default:
                return new NullCommand();
        }
    }
}
