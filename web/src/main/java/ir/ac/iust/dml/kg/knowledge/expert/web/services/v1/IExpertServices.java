package ir.ac.iust.dml.kg.knowledge.expert.web.services.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.expert.access.entities.Ticket;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Rest: /rs/v1/experts
 * SOA: /ws/v1/experts
 */
@WebService
@Path("/v1/experts")
@Api("/v1/experts")
public interface IExpertServices {
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Return list of users role",
            authorizations = {@Authorization("basic"), @Authorization("session")})
    List<String> login();

    @GET
    @Path("/triples/new")
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Get a list of new triple and return as ticket",
            authorizations = {@Authorization("basic"), @Authorization("session")})
    List<Ticket> triplesNew(@WebParam(name = "count") @QueryParam("count") int count);

    @GET
    @Path("/triples/current")
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod
    @ApiOperation(value = "Return a list of current assigned ticket",
            authorizations = {@Authorization("basic"), @Authorization("session")})
    PagingList<Ticket> triplesCurrent(
            @WebParam(name = "page") @QueryParam("page") int page,
            @WebParam(name = "pageCount") @QueryParam("pageCount") int pageCount
    );

}
