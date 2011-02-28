package com.inphina.sample;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sun.jersey.spi.inject.Inject;


@Path("/helloworld")
public class HelloWorldResource {
	
	@Inject
	private SpringService springService;
	
	private final Logger LOGGER = Logger.getLogger(HelloWorldResource.class.getName());

	@GET
	@Produces("application/json")
	public Person getSomeMessage() {
		springService.helloMe();
		return new Person("Vikas Hazrati", 36, "vhazrati@inphina.com");
	}

	@PUT
	@Consumes()
	public void setNewText(String newText) {
		LOGGER.info("Setting new text value |" + newText + "|");
	}
}