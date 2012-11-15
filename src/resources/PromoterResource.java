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
 * Controla los eventos propios para Promotores
 * @author Luis Roldan Chacon
 * @author Gilberth Arce Hernandez
 */
@Path("/promoter")
public class PromoterResource
{
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	private static Promoters promoters = Promoters.getInstance();
	private static Users users = Users.getInstance();
	private static RolesByUsers rolesByUsers = RolesByUsers.getInstance();

	/**
	 * Metodo que atiende una solicitud GET de un promotor y lista todos los promotores
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ArrayList<Promoter> List()
	{
		ArrayList<Promoter> values = new ArrayList<Promoter>();
		values.addAll(promoters.values());
		return values;
	}
	
	/**
	 * Metodo que atiende una solicitud GET de un promotor y obtiene su detalle segun su codigo
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/detail")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Promoter Detail(@QueryParam("code") int code)
	{
		return promoters.get(code);
	}
	
	/**
	 * Metodo que atiende una solicitud GET de un promotor y obtiene su detalle segun su usuario
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/byUsername")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Promoter getDetailByUsername(@QueryParam("username") String username)
	{
		return promoters.getByUsername(username);
	}
	
	/**
	 * Metodo que atiende una solicitud GET de un usuario y lo retoran segun su usuario
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@GET
	@Path("/detail/user")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public User DetailUser(@QueryParam("username") String username)
	{
		return users.get(username);
	}
	
	/**
	 * Metodo que atiende una solicitud POST de un promotor y lo inserta
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@POST
	public void Insert(@QueryParam("name") String name,@QueryParam("address") String address,@QueryParam("telephone") String telephone,@QueryParam("bank") String bank,@QueryParam("commision") int commision, @QueryParam("username") String username, @QueryParam("password") String password)
	{
		int code = (new Random()).nextInt();
		promoters.put(code, new Promoter(code, name, address, telephone, bank, commision ,username));
		users.put(username, new User(username, password));
		rolesByUsers.put(username + "$2", new RolesByUser(username, 2));
	}
	
	/**
	 * Metodo que atiende una solicitud PUT de un promotor y lo actualiza
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@PUT
	public void Update(@QueryParam("code") int code, @QueryParam("name") String name,@QueryParam("address") String address,@QueryParam("telephone") String telephone,@QueryParam("bank") String bank,@QueryParam("commision") int commision, @QueryParam("username") String username, @QueryParam("password") String password)
	{
		promoters.put(code, new Promoter(code, name, address, telephone, bank, commision ,username));
		users.put(username, new User(username, password));
		rolesByUsers.put(username + "$2", new RolesByUser(username, 2));
	}
	
	/**
	 * Metodo que atiende una solicitud DELETE de un poromotor y lo elimina
	 * @author Luis Roldan Chacon
	 * @author Gilberth Arce Hernandez
	 */
	@DELETE
	public void Delete(@QueryParam("code") int code, @QueryParam("username") String username)
	{
		promoters.remove(code);
		users.remove(username);
	}
}