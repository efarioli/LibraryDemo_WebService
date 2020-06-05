package command;

import handler.LoanManager;

/**
 *
 * @author gdm1
 */
public class GetLoanHistoryCommand implements Command
{
    private final int memberId;
    private final LoanManager manager;

    public GetLoanHistoryCommand(int memberId)
    {
        this.memberId = memberId;
        this.manager = new LoanManager();
    }
    
    @Override
    public Object execute()
    {
        return manager.getLoanHistoryForMember(memberId);
    }
    
}
