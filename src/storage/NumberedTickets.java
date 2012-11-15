package storage;

import beans.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Almacen de datos donde se guardan todos los tickets numerados
 * @see Location
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
public class NumberedTickets extends HashMap<String, NumberedTicket>
{
	private static String[][] data = {};

	private static NumberedTickets instance;
	/**
	 * Singleton
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public static NumberedTickets getInstance()
	{
		if (instance == null)
		{
			instance = new NumberedTickets();
		}
		return instance;
	}
	
	/**
	 * Constructor que inicializa el almacen de datos
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	private NumberedTickets()
	{
		for (int a = 0; a < data.length; a++)
		{
			NumberedTicket instance = new NumberedTicket();
			instance.setLocationNumber(Integer.parseInt(data[a][0]));
			instance.setTicketCode(Integer.parseInt(data[a][1]));
			super.put(instance.getLocationNumber()+ "$" + instance.getTicketCode(), instance);
		}
	}
	
	/**
	 * Obtiene todos los ticketes numerados que coincidan con un numero de ticket especifico
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public ArrayList<NumberedTicket> getByTicketCode(int ticketCode)
	{
		ArrayList<NumberedTicket> values = new ArrayList<NumberedTicket>();
		for (NumberedTicket numberedTicket : super.values())
		{
			if (numberedTicket.getTicketCode() == ticketCode)
			{
				values.add(numberedTicket);
			}
		}
		return values;
	}
}