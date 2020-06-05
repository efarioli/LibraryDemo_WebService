package command;

import dto.BookDTO;
import handler.BookManager;

/**
 *
 * @author gdm1
 */
public class AddCopiesToBookCommand implements Command
{
    private final BookDTO book;
    private final BookManager manager;

    public AddCopiesToBookCommand(BookDTO book)
    {
        this.book = book;
        this.manager = new BookManager();
    }
    
    @Override
    public Object execute()
    {
        manager.addCopiesToBook(book);
        return book;
    }
    
}
