/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dbase.MemberGateway;
import dto.BookDTO;
import dto.LibrarianDTO;
import dto.LoanDTO;
import dto.MemberDTO;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Base64;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
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
    public String getJson() {
        //TODO return proper representation object
        Command getBooksCommand = new GetAllBooksCommand();
        ArrayList<BookDTO> books = (ArrayList<BookDTO>) getBooksCommand.execute();
        return new Gson().toJson(books);
    }

    @GET
    @Path("checkloginmember")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkLogin(@HeaderParam("Authorization") String auth) {
        System.out.println("CHECK LOGIN MEMBER");
        String[] data = decodeString(auth);
        String userName = "";
        String password = "";
        try {
            //username and or password can be empty or null
            userName = data[0];
            password = data[1];
        } catch (Exception ex) {
        }

        MemberDTO m = (MemberDTO) CommandFactory
                .createCommand(
                        CommandFactory.CHECK_MEMBER_CREDENTIALS,
                        userName,
                        password)
                .execute();

        if (m != null) {
            return new Gson().toJson(m, MemberDTO.class);
        }
        System.out.println("INVALID CREDENTIALS");
        MemberDTO memberNull = new MemberDTO(0, "", "", "");

        return new Gson().toJson(memberNull, MemberDTO.class);
    }

    @GET
    @Path("checkloginadmin")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkLoginAdmin(@HeaderParam("Authorization") String auth) {
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

        if (librarian != null) {
            return new Gson().toJson(librarian, LibrarianDTO.class);
        }
        System.out.println("INVALID CREDENTIALS");

        LibrarianDTO libNull = new LibrarianDTO(0, "", "", "");
        return new Gson().toJson(libNull, LibrarianDTO.class);
    }

    private String[] decodeString(String auth) {
        String decodeString = "";
        String[] authParts = auth.split(" ");
        String authInfo = authParts[1];
        byte[] bytes = null;
        try {
            bytes = Base64.getDecoder().decode(authInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        decodeString = new String(bytes);
        String[] details = decodeString.split(":");
        return details;

    }

    private String[] checkForEmptyfields(String[] strArray) {
        String[] resArr = new String[2];
        String userName = "";
        String password = "";
        try {
            //username and or password can be empty or null
            userName = strArray[0];
            password = strArray[1];
        } catch (Exception ex) {
        }
        resArr[0] = userName;
        resArr[1] = password;
        return resArr;
    }
    @GET
    @Path("members/{userid}/loans")
    @Produces(MediaType.APPLICATION_JSON)
    public String currentLoansForMember(@PathParam("userid") int userId)
    {
        System.out.println("CURRENT LOANS FOR MEMBER");
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC).create();
        //the exclude modifier static - make the transient property been ignored

        MemberGateway mg = new MemberGateway();
        MemberDTO member = mg.findMemberByUserId(userId);
        ArrayList<LoanDTO> loans = (ArrayList<LoanDTO>) CommandFactory
                .createCommand(
                        CommandFactory.CURRENT_LOANS_FOR_MEMBER,
                        member)
                .execute();

//        System.out.println(gson.toJson(loans));
        return gson.toJson(loans);
    }
}
