package command;

import dto.MemberDTO;
import handler.LoanManager;

/**
 *
 * @author gdm1
 */
public class GetCurrentLoansCommand implements Command
{
    private final MemberDTO member;
    private final LoanManager manager;

    public GetCurrentLoansCommand(MemberDTO member)
    {
        this.member = member;
        this.manager = new LoanManager();
    }
    
    @Override
    public Object execute()
    {
        return manager.findCurrentLoansForMember(member);
    }
    
}
