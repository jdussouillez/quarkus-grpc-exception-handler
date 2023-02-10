# quarkus-grpc-exception-handler

Simple project (based on Quarkus quickstart project) to reproduce a bug with the gRPC exception handler

- https://quarkus.io/guides/grpc-service-consumption#custom-exception-handling
- https://github.com/quarkusio/quarkus/pull/29419/files
- https://stackoverflow.com/questions/70832902/serverinterceptor-grpc-not-catching-exceptions-with-smallrye-mutiny-reactive-in/70878529#70878529

## Step to reproduce

- Run the server : `./mvnw quarkus:dev`
- Use gRPC client (Postman, grpcurl, etc) and call the `Greeter / SayHello` (on `localhost:8080`) service with message `{"name": "internal"}`
- The service throws an exception and the `HelloExceptionHandlerProvider` turns it into a `StatusException` with "INTERNAL" gRPC status.
- On the client, the status is "UNKNOWN" but it should be "INTERNAL" because [`HelloExceptionHandler::handleException`](https://github.com/jdussouillez/quarkus-grpc-exception-handler/blob/master/src/main/java/io/quarkus/grpc/examples/hello/HelloExceptionHandlerProvider.java#L53) is not called.
