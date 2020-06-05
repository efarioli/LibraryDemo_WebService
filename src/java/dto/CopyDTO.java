package dto;

import java.io.Serializable;

/**
 *
 * @author gdm1
 */
public class CopyDTO implements Serializable
{
    private final int id;
    private final BookDTO book;
    private final boolean referenceOnly;
    private final boolean onLoan;

    public CopyDTO(int id, BookDTO book, boolean referenceOnly, boolean onLoan)
    {
        this.id = id;
        this.book = book;
        this.referenceOnly = referenceOnly;
        this.onLoan = onLoan;
    }

    public int getId()
    {
        return id;
    }

    public BookDTO getBook()
    {
        return book;
    }
    
    public String getStatus()
    {
        return isReferenceOnly() ? "Reference only" : (isOnLoan() ? "On loan" : "Available");
    }

    public boolean isOnLoan()
    {
        return onLoan;
    }

    public boolean isReferenceOnly()
    {
        return referenceOnly;
    }
}
