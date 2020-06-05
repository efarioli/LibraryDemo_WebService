package handler;

import dbase.LibrarianGateway;
import dto.LibrarianDTO;

/**
 *
 * @author gdm1
 */
public class LibrarianManager
{
    private final LibrarianGateway gateway = new LibrarianGateway();
    
    public LibrarianDTO findLibrarianByUsername(String username)
    {
        return gateway.findLibrarianByUsername(username);
    }
}
