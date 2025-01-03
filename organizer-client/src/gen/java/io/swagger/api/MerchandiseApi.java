package io.swagger.api;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.ext.multipart.*;

import io.swagger.model.merchandise_provider.Merchandise;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Merchandise provider
 *
 * <p>This is a RESTful API that allows management and access to data about event merchandise.
 *
 */
@Path("/")
public interface MerchandiseApi  {

	/**
     * Register this merchandise to an event in database.
     *
     */
    @PUT
    @Path("/merch/addevent/{eventId}/to/{merchId}")
    @Produces({ "application/json" })
    @Operation(summary = "Register this merchandise to an event in database.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Return the merchandise object with the new event id.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Merchandise.class))),
        @ApiResponse(responseCode = "400", description = "Either ID was invalid or null/missing."),
        @ApiResponse(responseCode = "500", description = "Unexpected error occured on server.") })
    public Merchandise addToEvent1(@PathParam("eventId") String eventId, @PathParam("merchId") Long merchId);

    /**
     * Add a new merchandise article to the database.
     *
     */
    @POST
    @Path("/merch/create")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Add a new merchandise article to the database.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Return the merchandise object with the newly generated id.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Merchandise.class))),
        @ApiResponse(responseCode = "400", description = "Invalid parameters provided."),
        @ApiResponse(responseCode = "500", description = "Unexpected error occured on server.") })
    public Merchandise create1(Merchandise body);

    /**
     * Return the entire list of merchandise.
     *
     */
    @GET
    @Path("/merch/all")
    @Produces({ "application/json" })
    @Operation(summary = "Return the entire list of merchandise.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Return all the content of the database.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Merchandise.class))) })
    public Merchandise getAll1();
    
    /**
     * Return a page of merchandise recorded on the database, sorted using one of 4 methods (ID_ASC, ID_DESC, ALPHABETICAL_ASC, ALPHABETICAL_DESC).
     *
     */
    @GET
    @Path("/merch")
    @Produces({ "application/json" })
    @Operation(summary = "Return a page of merchandise recorded on the database, sorted using one of 4 methods (ID_ASC, ID_DESC, ALPHABETICAL_ASC, ALPHABETICAL_DESC).", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Return the selected page of merchandise, using the provided sorting method.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Merchandise.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid parameters provided."),
        @ApiResponse(responseCode = "500", description = "Unexpected error occured on server.") })
    public List<Merchandise> getMerchandisePage1(@QueryParam("page")Integer page, @QueryParam("sortby")String sortby);

    /**
     * Return the list of merchandise for an event.
     *
     */
    @GET
    @Path("/merch/event/{eventId}")
    @Produces({ "application/json" })
    @Operation(summary = "Return the list of merchandise for an event.", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Return the merchandise of the event.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Merchandise.class))),
        @ApiResponse(responseCode = "400", description = "Invalid parameters provided."),
        @ApiResponse(responseCode = "500", description = "Unexpected error occured on server.") })
    public Merchandise getEventMerchandise1(@PathParam("eventId") Long eventId);
}
