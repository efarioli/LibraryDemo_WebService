package command;

import dto.LoanDTO;
import handler.LoanManager;
import java.util.Calendar;

/**
 *
 * @author gdm1
 */
public class ReturnBookCommand implements Command
{
    private final LoanDTO loan;
    private final LoanManager manager;

    public ReturnBookCommand(LoanDTO loan)
    {
        Calendar now = Calendar.getInstance();
        this.loan = new LoanDTO(
                loan.getId(), 
                loan.getMember(), 
                loan.getCopy(), 
                loan.getLoanDate(), 
                loan.getDueDate(), 
                now, 
                loan.getNumberOfRenewals());
        this.manager = new LoanManager();
    }
    
    @Override
    public Object execute()
    {
        return manager.returnBook(loan);
    }
    
}
