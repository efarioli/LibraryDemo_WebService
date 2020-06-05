package command;

import handler.BookManager;

/**
 *
 * @author gdm1
 */
public class GetAllBooksCommand implements Command
{
    private final BookManager manager;

    public GetAllBooksCommand()
    {
        this.manager = new BookManager();
    }
    
    @Override
    public Object execute()
    {
        return manager.getAllBooks();
    }
    
}
