package command;

import dto.BookDTO;
import handler.BookManager;

/**
 *
 * @author gdm1
 */
public class CreateCopiesForBookCommand implements Command
{
    private final int bookId;
    private final int numCopies;
    private final boolean referenceOnly;
    private final BookManager manager;

    public CreateCopiesForBookCommand(int bookId, int numCopies, boolean referenceOnly)
    {
        this.bookId = bookId;
        this.numCopies = numCopies;
        this.referenceOnly = referenceOnly;
        this.manager = new BookManager();
    }
    
    @Override
    public Object execute()
    {
        manager.createCopies(bookId, numCopies, referenceOnly);
        return bookId;
    }
    
}
