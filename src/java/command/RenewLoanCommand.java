package command;

import dto.LoanDTO;
import handler.LoanManager;

/**
 *
 * @author gdm1
 */
public class RenewLoanCommand implements Command
{
    private final LoanDTO loan;
    private final LoanManager manager;

    public RenewLoanCommand(LoanDTO loan)
    {
        this.loan = loan;
        this.manager = new LoanManager();
    }
    
    @Override
    public Object execute()
    {
        return manager.renewLoan(loan);
    }
    
}
