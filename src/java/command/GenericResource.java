/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dbase.BookGateway;
import dbase.MemberGateway;
import dto.BookDTO;
import dto.CopyDTO;
import dto.LibrarianDTO;
import dto.LoanDTO;
import dto.MemberDTO;
import dto.ResultDTO;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Base64;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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
    public String currentLoansForMember(@PathParam("userid") int userId) {
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

    @GET
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public String GetAllBooks() {
        System.out.println("GET ALL THE BOOKS");
        ArrayList<BookDTO> books = (ArrayList<BookDTO>) CommandFactory
                .createCommand(
                        CommandFactory.ALL_BOOKS)
                .execute();
        return new Gson().toJson(books);
    }

    @GET
    @Path("books/{bookid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String GetBookWithCopies(@PathParam("bookid") int bookid) {
        System.out.println("GET BOOK WITH COPIES");
        BookGateway bg = new BookGateway();
        BookDTO book = bg.findBook(bookid);
        BookDTO bookToDisplay = (BookDTO) CommandFactory
                .createCommand(
                        CommandFactory.ADD_COPIES_TO_BOOK,
                        book)
                .execute();
        ArrayList<CopyDTO> copies = book.getCopies();
        return new Gson().toJson(bookToDisplay);
    }

    @POST
    @Path("books/{bookid}/copies/{numofcopyes}/{isref}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addCopies(@PathParam("bookid") int bookid, @PathParam("numofcopyes") int num, @PathParam("isref") boolean isref) {
        System.out.println("ADD COPIES" + isref);

        CommandFactory
                .createCommand(
                        CommandFactory.CREATE_COPIES_FOR_BOOK,
                        bookid,
                        num,
                        isref)
                .execute();
        return GetBookWithCopies(bookid);
    }

    @PUT
    @Path("loans/{loanid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String renewLoan(@PathParam("loanid") int loanid, String loanJsonString) {
        System.out.println("#################################");
        System.out.println("Renew Loan");
        System.out.println(loanJsonString);
        LoanDTO loan = new Gson().fromJson(loanJsonString, LoanDTO.class);
        CommandFactory
                .createCommand(
                        CommandFactory.RENEW_LOAN,
                        loan)
                .execute();
        return currentLoansForMember(loan.getMember().getId());
    }

    @POST
    @Path("loans/history/{loanid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addBookToLoanHistory(@PathParam("loanid") int loanid, String loanJsonString) {

        System.out.println("#################################");
        System.out.println("RETURN BOOK :  loanid:" + loanid);

        LoanDTO loan = new Gson().fromJson(loanJsonString, LoanDTO.class);

        CommandFactory
                .createCommand(
                        CommandFactory.RETURN_BOOK,
                        loan)
                .execute();
        return currentLoansForMember(loan.getMember().getId());

    }

    @PUT
    @Path("member/{memberid}/borrowcopy/{copyid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String borrowCopy(@PathParam("memberid") int memberId, @PathParam("copyid") int copyId) {

        System.out.println("#################################");
        System.out.println("Borrow Copy");
        System.out.println("Member id " + memberId);
        System.out.println("Copy id " + copyId);

        CommandFactory
                .createCommand(
                        CommandFactory.BORROW_BOOK,
                        memberId,
                        copyId)
                .execute();
        return new Gson().toJson(new ResultDTO("one copy borrowed"));
    }
}
