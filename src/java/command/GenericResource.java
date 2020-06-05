/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import com.google.gson.Gson;
import dto.BookDTO;
import dto.LibrarianDTO;
import dto.MemberDTO;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author zeke
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of command.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson()
    {
        //TODO return proper representation object
        Command getBooksCommand = new GetAllBooksCommand();
        ArrayList<BookDTO> books = (ArrayList<BookDTO>) getBooksCommand.execute();
        return new Gson().toJson(books);
    }
     @GET
    @Path("checkloginmember")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkLogin(@HeaderParam("Authorization") String auth)
    {
        System.out.println("CHECK LOGIN MEMBER");
        String[] data = decodeString(auth);
        String userName = "";
        String password = "";
        try
        {
            //username and or password can be empty or null
            userName = data[0];
            password = data[1];
        } catch (Exception ex)
        {
        }

        MemberDTO m = (MemberDTO) CommandFactory
                .createCommand(
                        CommandFactory.CHECK_MEMBER_CREDENTIALS,
                        userName,
                        password)
                .execute();

        if (m != null)
        {
            return new Gson().toJson(m, MemberDTO.class);
        }
        System.out.println("INVALID CREDENTIALS");
        MemberDTO memberNull = new MemberDTO(0, "", "", "");

        return new Gson().toJson(memberNull, MemberDTO.class);
    }
    @GET
    @Path("checkloginadmin")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkLoginAdmin(@HeaderParam("Authorization") String auth)
    {
        System.out.println("CHECK LOGIN ADMIN");
        String[] data = decodeString(auth);
        String[] data2 = checkForEmptyfields(data);
        String userName = data2[0];
        String password = data2[1];
        LibrarianDTO librarian = (LibrarianDTO) CommandFactory
                .createCommand(
                        CommandFactory.CHECK_LIBRARIAN_CREDENTIALS,
                        userName,
                        password)
                .execute();

        if (librarian != null)
        {
            return new Gson().toJson(librarian, LibrarianDTO.class);
        }
        System.out.println("INVALID CREDENTIALS");

        LibrarianDTO libNull = new LibrarianDTO(0, "", "", "");
        return new Gson().toJson(libNull, LibrarianDTO.class);
    }
}
