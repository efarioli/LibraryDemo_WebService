package handler;

import dbase.BookGateway;
import dto.BookDTO;
import java.util.ArrayList;

/**
 *
 * @author gdm1
 */
public class BookManager
{
    
    private final BookGateway gateway = new BookGateway();
    
    public void addCopiesToBook(BookDTO book)
    {
        gateway.addCopiesToBook(book);
    }
    
    public void createCopies(int bookId, int numCopies, boolean referenceOnly)
    {
        gateway.createCopies(bookId, numCopies, referenceOnly);
    }
    
    public ArrayList<BookDTO> getAllBooks()
    {
        return gateway.findAllBooks();
    }
}
