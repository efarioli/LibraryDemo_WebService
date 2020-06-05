package handler;

import dbase.MemberGateway;
import dto.MemberDTO;

/**
 *
 * @author gdm1
 */
public class MemberManager
{
    private final MemberGateway gateway = new MemberGateway();
    
    public MemberDTO findMemberByUsername(String username)
    {
        return gateway.findMemberByUsername(username);
    }
}
