package http.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class IndexController {

	@GET
	public Response show() {
		return Response.ok("indexing").build();
	}
}
