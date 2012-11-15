package resources;

import beans.*;
import storage.*;
import java.net.URI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
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
 * Controla los eventos propios para la pantalla de eventos
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
@Path("/event")
public class EventResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	private static Events events = Events.getInstance();
	private static Locations locations = Locations.getInstance();
	private static Tickets tickets = Tickets.getInstance();
	
	/**
	 * Metodo que atiende una solicitud GET y devuelve una lista de eventos
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ArrayList<Event> List()
	{
		ArrayList<Event> values = new ArrayList<Event>();
		values.addAll(events.values());
		return values;
	}
	
	/**
	 * Metodo que atiende una solicitud GET y devuelve una lista de eventos asiciados a un promotor
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/byPromoter")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ArrayList<Event> ListByPromoter(@QueryParam("promoterCode") int promoterCode)
	{
		return events.getByPromoterCode(promoterCode);
	}
	
	/**
	 * Metodo que atiende una solicitud GET y devuelve el detalle de un evento
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/details")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Event EventDetail(@QueryParam("eventCode") int eventCode)
	{
		return events.get(eventCode);
	}

	/**
	 * Metodo que atiende una solicitud GET y devuelve una lista de localidades asociadas a un evento
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/details/location")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ArrayList<Location> getLocationsByEventCode(@QueryParam("eventCode") int eventCode)
	{
		return locations.getByEventCode(eventCode);
	}
	
	/**
	 * Metodo que atiende una solicitud GET y obtiene los tickets asociados a un evento
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/details/ticket")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ArrayList<Ticket> getTicketsByEventCode(@QueryParam("eventCode") int eventCode)
	{
		return tickets.getByEventCode(eventCode);
	}

	/**
	 * Metodo que atiende una solicitud POST e inserta un evento
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@POST
	public void Insert(@QueryParam("code") int code,@QueryParam("name") String name,@QueryParam("eventType") int eventType,@QueryParam("artists") String artists,@QueryParam("date") String date,@QueryParam("place") String place, @QueryParam("promoterCode") int promoterCode)
	{
		events.put(code , new Event(code, name, eventType, artists, date, place, promoterCode));
	}
	
	/**
	 * Metodo que atiende una solicitud POST e inserta una localidad
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@POST
	@Path("/details/location")
	public void saveLocation(@QueryParam("locationType") int locationType, @QueryParam("eventCode") int eventCode, @QueryParam("price") int price, @QueryParam("quantity") int quantity, @QueryParam("numbered") boolean numbered)
	{
		locations.put(locationType + "$" + eventCode, new Location(locationType, eventCode, price, quantity, numbered));
	}
	
	/**
	 * Metodo que atiende una solicitud PUT y actualiza un evento
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@PUT
	public void updateEvent(@QueryParam("code") int code,@QueryParam("name") String name,@QueryParam("eventType") int eventType,@QueryParam("artists") String artists,@QueryParam("date") String date,@QueryParam("place") String place, @QueryParam("promoterCode") int promoterCode)
	{
		events.put(code ,new Event(code, name, eventType, artists, date, place, promoterCode));
	}
	
	/**
	 * Metodo que atiende una solicitud DELETE y elimina un evento y sus localidades
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@DELETE
	public void deleteEvent(@QueryParam("code") int code) throws Exception
	{
		deleteLocationsByEvent(code);
		events.remove(code);
	}
	
	/**
	 * Metodo que atiende una solicitud DELETE y elimina las localidades de un evento
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@DELETE
	@Path("/details/location")
	public void deleteLocationsByEventResource(@QueryParam("eventCode") int eventCode)
	{
		deleteLocationsByEvent(eventCode);
	}
	
	/**
	 * Metodo que elimina todas las localidades asociadas a un evento
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	public void deleteLocationsByEvent(int eventCode)
	{
		ArrayList<String> keys = new ArrayList<String>();
		for (Location location : locations.values())
		{
			if (location.getEventCode() == eventCode)
			{
				keys.add(location.getLocationType() + "$" + eventCode);
			}
		}
		for (String key : keys)
		{
			locations.remove(key);
		}
	}
}