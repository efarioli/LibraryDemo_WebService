package handler;

import dbase.LoanGateway;
import dto.LoanDTO;
import dto.MemberDTO;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author gdm1
 */
public class LoanManager
{

    private final LoanGateway gateway = new LoanGateway();

    public boolean borrowBook(int memberId, int copyId, Calendar loanDate, Calendar dueDate)
    {
        return gateway.borrowBook(memberId, copyId, loanDate, dueDate);
    }

    public ArrayList<LoanDTO> findCurrentLoansForMember(MemberDTO member)
    {
        return gateway.findCurrentLoansForMember(member);
    }

    public ArrayList<LoanDTO> getLoanHistoryForMember(int memberId)
    {
        return gateway.getLoanHistoryForMember(memberId);
    }

    public boolean renewLoan(LoanDTO loan)
    {
        if (loan.isRenewable())
        {
            return gateway.renewLoan(loan);
        }
        return false;
    }

    public boolean returnBook(LoanDTO loan)
    {
        boolean archived = gateway.archiveLoan(
                loan.getMember().getId(),
                loan.getCopy().getId(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getNumberOfRenewals());
        
        boolean deleted = gateway.deleteLoan(loan.getId());
        
        return archived && deleted;
    }
}
