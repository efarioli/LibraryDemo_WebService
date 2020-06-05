package dto;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author gdm1
 */
public class LoanDTO implements Serializable
{
    private static final int MAX_RENEWALS = 3;
    private final int id;
    private final MemberDTO member;
    private final CopyDTO copy;
    private final Calendar loanDate;
    private Calendar dueDate;
    private Calendar returnDate;
    private int numberOfRenewals;

    public LoanDTO(int id, MemberDTO member, CopyDTO copy, Calendar loanDate, Calendar dueDate, Calendar returnDate, int numberOfRenewals)
    {
        this.id = id;
        this.member = member;
        this.copy = copy;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.numberOfRenewals = numberOfRenewals;
    }

    public int getId()
    {
        return id;
    }

    public MemberDTO getMember()
    {
        return member;
    }

    public CopyDTO getCopy()
    {
        return copy;
    }

    public Calendar getLoanDate()
    {
        return loanDate;
    }

    public Calendar getDueDate()
    {
        return dueDate;
    }

    public Calendar getReturnDate()
    {
        return returnDate;
    }

    public int getNumberOfRenewals()
    {
        return numberOfRenewals;
    }
    
    public boolean isRenewable()
    {
        return numberOfRenewals < MAX_RENEWALS;
    }

    public void setDueDate(Calendar dueDate)
    {
        this.dueDate = dueDate;
    }

    public void setReturnDate(Calendar returnDate)
    {
        this.returnDate = returnDate;
    }

    public void setNumberOfRenewals(int numberOfRenewals)
    {
        this.numberOfRenewals = numberOfRenewals;
    }
    
}
