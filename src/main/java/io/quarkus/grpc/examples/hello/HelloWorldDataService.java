package io.quarkus.grpc.examples.hello;

import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloWorldDataService {

    public Uni<String> sayHello(final String name) {
        if (name.equals("internal")) {
            return Uni.createFrom().failure(new HelloException("ERROR_CODE_INTERNAL"));
        }
        return Uni.createFrom().item("Hello " + name);
    }
}
