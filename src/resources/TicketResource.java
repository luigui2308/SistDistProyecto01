package resources;

import beans.*;
import storage.*;
import java.net.URI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.NotFoundException;

/**
 * Recurso para controlar las peticiones de la pantalla de tiquetes
 * @see Ticket
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
@Path("/ticket")
public class TicketResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	private static Events events = Events.getInstance();
	private static Tickets tickets = Tickets.getInstance();
	private static Customers customers = Customers.getInstance();
	private static Locations locations = Locations.getInstance();
	private static NumberedTickets numberedTickets = NumberedTickets.getInstance();

	/**
	 * Metodo que atiende una solicitud POST de un cliente y lo inserta
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@POST
	@Path("/customer")
	public void updateCustomer(@QueryParam("id") String id,@QueryParam("name") String name,@QueryParam("address") String address,@QueryParam("telephone") String telephone,@QueryParam("cardnumber") String cardnumber,@QueryParam("cardType") String cardType)
	{
		customers.put(id, new Customer(id, name, address, telephone, cardnumber, cardType));
	}
	
	/**
	 * Metodo que atiende una solicitud POST de un ticket y lo inserta
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@POST
	public void insert(@QueryParam("code") int code,@QueryParam("locationType") int locationType,@QueryParam("eventCode") int eventCode,@QueryParam("customerId") String customerId,@QueryParam("quantity") int quantity, @QueryParam("numbered") int numbered)
	{
		Ticket ticket = new Ticket(code, locationType, eventCode, customerId, (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(Calendar.getInstance().getTime()), quantity, 0);
		Location location = locations.get(ticket.getLocationType() + "$" + ticket.getEventCode());
		if (ticket.getQuantity() + getNumberOfTicketsByEventAndLocation(ticket.getEventCode(), ticket.getLocationType()) <= location.getQuantity())
		{
			ticket.setAmount(ticket.getQuantity() * location.getPrice());
			tickets.put(ticket.getCode(), ticket);
			if (location.getNumbered())
			{
				int offset = numbered;
				if (offset > 1 && offset <= location.getQuantity())
				{
					int up = offset, down = offset - 1, needed = ticket.getQuantity();
					while (needed > 0)
					{
						if (up <= location.getQuantity())
						{
							if(!ticketExists(up, ticket.getEventCode(), ticket.getLocationType()))
							{
								numberedTickets.put(up + "$" + ticket.getCode(), new NumberedTicket(up, ticket.getCode()));
								needed--;
							}
							up++;
							if (needed <= 0) break;
						}
						if (down > 0)
						{
							if (!ticketExists(down, ticket.getEventCode(), ticket.getLocationType()))
							{
								numberedTickets.put(down + "$" + ticket.getCode(), new NumberedTicket(down, ticket.getCode()));
								needed--;
							}
							down--;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Metodo que atiende una solicitud GET de un ticket y lo devuelve segun su codigo
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	public Ticket getTicket(@QueryParam("ticketCode") int ticketCode)
	{
		return tickets.get(ticketCode);
	}
	
	/**
	 * Metodo que atiende una solicitud GET de un cliente y lo retorna segun su usuario
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/customer")
	public Customer getCustomer(@QueryParam("username") String username)
	{
		return customers.get(username);
	}
	
	/**
	 * Metodo que atiende una solicitud GET de un ticket numerado y retorna todos los tickets numerados asociados a un ticket
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/numberedTickets")
	public ArrayList<NumberedTicket> getNumberedTicketsByTicketCode(@QueryParam("ticketCode") int ticketCode)
	{
		return numberedTickets.getByTicketCode(ticketCode);
	}
	
	/**
	 * Obtiene el numero de ticket's comprados para un evento y un tipo de localidad
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public int getNumberOfTicketsByEventAndLocation(int eventCode, int locationTypeId)
	{
		int numberOfTickets = 0;
		for (Ticket ticket : tickets.findByEventAndLocation(eventCode, locationTypeId))
		{
			numberOfTickets += ticket.getQuantity();
		}
		return numberOfTickets;
	}
	
	/**
	* Método que permite obtener la existencia de un ticket numerado
	* @return true si hay un ticket con la misma numeracion
	* @author Luis Roldan Chacon
	* @author Gilberth Arce Hernandez
	*/
	public boolean ticketExists(int locationNumber, int eventCode, int locationType)
	{
		for (Ticket ticket : tickets.findByEventAndLocation(eventCode, locationType))
		{
			if (numberedTickets.get(locationNumber + "$" + ticket.getCode()) != null)
			{
				return true;
			}
		}
		return false;
	}
}