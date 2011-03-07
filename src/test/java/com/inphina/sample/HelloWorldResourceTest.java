package com.inphina.sample;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class HelloWorldResourceTest {

	private SelectorThread threadSelector;

	private WebResource webresource;

	@Before
	public void setUp() throws Exception {

		threadSelector = GrizzlyMain.startServer();

		ClientConfig clientConfiguration = new DefaultClientConfig();
		// use the following jaxb context resolver
		// clientConfiguration.getClasses().add(JAXBContextResolver.class);
		Client client = Client.create(clientConfiguration);
		webresource = client.resource(GrizzlyMain.baseUri);
	}
	
	@After
	public void tearDown() throws Exception {
		threadSelector.stopEndpoint();
	}

	@Test
	public void testApplicationWadl() {
		System.out.println("#############" + webresource.getURI());
		String applicationWadl = webresource.path("application.wadl").get(String.class);
		Assert.assertTrue("Something wrong. Returned wadl length is not > 0", applicationWadl.length() > 0);
	}
	
	@Test
	public void getOnHelloWorldPath() {
		// get the initial representation
		String s = webresource.path("helloworld").accept("application/json").get(String.class);
		Assert.assertEquals("{\"name\":\"Vikas Hazrati\",\"age\":36,\"email\":\"vhazrati@inphina.com\"}", s);
		System.out.println(s);
	}

}
