package dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author gdm1
 */
public class BookDTO implements Serializable
{
    private final int id;
    private final String title;
    private final String author;
    private final String isbn;
    private final ArrayList<CopyDTO> copies;

    public BookDTO(int id, String title, String author, String isbn)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.copies = new ArrayList<>();
    }

    public void addCopy(CopyDTO copy)
    {
        copies.add(copy);
    }
    
    public ArrayList<CopyDTO> getCopies()
    {
        return copies;
    }
    
    public int getId()
    {
        return id;
    }
    
    public int getNumberOfCopies()
    {
        return copies.size();
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getIsbn()
    {
        return isbn;
    }
    
}
