package io.quarkus.grpc.examples.hello;

import examples.Greeter;
import examples.HelloReply;
import examples.HelloRequest;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import javax.inject.Inject;

@GrpcService
public class HelloWorldService implements Greeter {

    @Inject
    protected HelloWorldDataService helloWorldDataService;

    @Override
    public Uni<HelloReply> sayHello(final HelloRequest request) {
        return helloWorldDataService.sayHello(request.getName())
            .map(res -> HelloReply.newBuilder().setMessage(res).setCount(-1).build());
    }
}
