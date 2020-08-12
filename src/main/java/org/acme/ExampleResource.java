package org.acme;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class ExampleResource {

    @Channel("outbound")
    Emitter<String> emitter;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        for(int i=1;i<=100;i++) {
            emitter.send("Teste "+ i);
        }

        return "hello";
    }
}