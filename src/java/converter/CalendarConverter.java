package converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author gdm1
 */
@FacesConverter(value = "calendarConverter")
public class CalendarConverter implements Converter
{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

    @Override
    public Object getAsObject(FacesContext context,
            UIComponent component,
            String newValue)
            throws ConverterException
    {
        try
        {
            return sdf.parse(newValue);
        }
        catch (ParseException e)
        {
            throw new ConverterException("String did not convert to a date");
        }
    }

    @Override
    public String getAsString(FacesContext context,
            UIComponent component,
            Object value)
            throws ConverterException
    {
        if (value == null ||
                !(value instanceof Calendar))
        {
            return "";
        }
        Calendar cal = (Calendar)value;
        return sdf.format(cal.getTime());
    }

}
