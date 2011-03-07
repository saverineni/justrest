/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved. 
 * 
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (the "License").  You may not use this file
 * except in compliance with the License. 
 * 
 * You can obtain a copy of the License at:
 *     https://jersey.dev.java.net/license.txt
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * When distributing the Covered Code, include this CDDL Header Notice in each
 * file and include the License file at:
 *     https://jersey.dev.java.net/license.txt
 * If applicable, add the following below this CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 *     "Portions Copyrighted [year] [name of copyright owner]"
 */

package com.inphina.sample;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.sun.grizzly.http.SelectorThread;
import com.sun.grizzly.http.servlet.ServletAdapter;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

public class GrizzlyMain {
    
    private static int getPort(int defaultPort) {
        String port = System.getenv("JERSEY_HTTP_PORT");
        if (null != port) {
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;        
    } 
    
    final static URI baseUri = UriBuilder.fromUri( "http://localhost/" ).port( 9998 ).build();
    
    public static SelectorThread startServer() throws IOException{
    	final ServletAdapter adapter =new ServletAdapter();
    	adapter.addInitParameter( "com.sun.jersey.config.property.packages", "com.inphina.sample" );
    	adapter.addInitParameter( "com.sun.jersey.api.json.POJOMappingFeature", "true" );
    	adapter.addContextParameter( "contextConfigLocation","classpath:applicationContext.xml"  );
    	adapter.addServletListener( "org.springframework.web.context.ContextLoaderListener" );
    	adapter.setServletInstance( new SpringServlet() );
    	adapter.setContextPath(baseUri.getPath());
    	adapter.setProperty( "load-on-startup", 1 );

    	System.out.println("********" + baseUri.getPath());
        SelectorThread threadSelector = GrizzlyServerFactory.create(baseUri, adapter);
        return threadSelector;
    }
    
    
    
    public static void main(String[] args) throws IOException {
        System.out.println("Starting grizzly...");
        SelectorThread threadSelector = startServer();
        System.out.println(String.format(
                "Jersey app started with WADL available at %sapplication.wadl\n" +
                "Hit enter to stop it...", baseUri));
        System.in.read();
        threadSelector.stopEndpoint();
    }    
}
