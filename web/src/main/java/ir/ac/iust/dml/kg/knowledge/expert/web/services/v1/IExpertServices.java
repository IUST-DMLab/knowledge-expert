package ir.ac.iust.dml.kg.knowledge.expert.web.services.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @ApiOperation(value = "Return list of users role", authorizations = @Authorization("basic"))
    List<String> login();

}
