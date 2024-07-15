package it.univaq.sose.dagi.merchandising_rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.univaq.sose.dagi.merchandising_rest.model.Merchandise;
import it.univaq.sose.dagi.merchandising_rest.service.MerchandiseService;
import it.univaq.sose.dagi.merchandising_rest.service.MerchandiseServiceDummyImpl;

@RestController
@RequestMapping("/merch")
public class MerchController {
	
	//Service (injected)
	private final MerchandiseService merchandiseService;

	
	//@Autowired is inferred by Spring Boot when there is a single public constructor
	//@Autowired
	public MerchController(MerchandiseServiceDummyImpl merchandiseServiceImpl) {
		this.merchandiseService = merchandiseServiceImpl;
	}

	@Operation(summary = "Return the entire list of merchandise.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return all the content of the database.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Merchandise.class)
								  ) 
				  }),
//		  @ApiResponse(
//				  responseCode = "400",
//				  description = "Invalid addendum(s) provided", 
//				  content = {
//						  @Content(
//								  mediaType = "application/json"
//								  )
//				  }), 
//		  @ApiResponse(
//				  responseCode = "500",
//				  description = "Message template not found", 
//				  content = {
//						  @Content(
//								  mediaType = "application/json"
//								  )
//				  }) 
	})
	@GetMapping("/all")
	public ResponseEntity<List<Merchandise>> getAll() {
		List<Merchandise> merchList = merchandiseService.getAll();
		return new ResponseEntity<List<Merchandise>>(merchList, HttpStatus.OK);
	}
	
	@Operation(summary = "Return the list of merchandise for an event.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return the merchandise of the event.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Merchandise.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Invalid parameters provided.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured on server.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@GetMapping("/event/{id}")
	public ResponseEntity<List<Merchandise>> getEventMerchandise(@PathVariable Long id) {
		List<Merchandise> merchList = merchandiseService.getByEvent(id);
		return new ResponseEntity<List<Merchandise>>(merchList, HttpStatus.OK);
	}
	
	@Operation(summary = "Add a new merchandise article to the database.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return the merchandise object with the newly generated id.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Merchandise.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Invalid parameters provided.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured on server.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@PostMapping("/create")
	public ResponseEntity<Merchandise> create(@RequestBody Merchandise newMerch) {
		System.out.println(newMerch);
		Merchandise merchWithId = merchandiseService.save(newMerch);
		System.out.println(merchWithId.getId());
		return new ResponseEntity<Merchandise>(merchWithId, HttpStatus.OK);
	}
	
	@Operation(summary = "Add a new merchandise article to the database.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "Return the merchandise object with the newly generated id.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Merchandise.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Either ID was invalid or null/missing.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured on server.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@PutMapping("/addevent/{eventId}/to/{merchId}")
	public ResponseEntity<Merchandise> addToEvent(@PathVariable Long eventId, @PathVariable Long merchId) {
		Merchandise updatedMerch = merchandiseService.addEventToMerch(eventId, merchId);
		return new ResponseEntity<Merchandise>(updatedMerch, HttpStatus.OK);
	}
}
