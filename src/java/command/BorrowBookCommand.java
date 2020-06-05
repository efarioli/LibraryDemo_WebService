package command;

import dto.LoanDTO;
import handler.LoanManager;
import java.util.Calendar;

/**
 *
 * @author gdm1
 */
public class BorrowBookCommand implements Command
{

    private final int memberId;
    private final int copyId;
    private final Calendar loanDate;
    private final Calendar dueDate;
    private final LoanManager manager;

    public BorrowBookCommand(int memberId, int copyId)
    {
        this.memberId = memberId;
        this.copyId = copyId;
        this.loanDate = Calendar.getInstance();
        this.dueDate = (Calendar) loanDate.clone();
        dueDate.add(Calendar.DAY_OF_MONTH, 14);;
        this.manager = new LoanManager();
    }

    @Override
    public Object execute()
    {
        return manager.borrowBook(memberId, copyId, loanDate, dueDate);
    }

}
