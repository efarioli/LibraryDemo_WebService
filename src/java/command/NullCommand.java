package command;

/**
 *
 * @author gdm1
 */
public class NullCommand implements Command
{
    @Override
    public Object execute()
    {
        return null;
    }
}
