package io.quarkus.grpc.examples.hello;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.StatusException;
import io.quarkus.grpc.ExceptionHandler;
import io.quarkus.grpc.ExceptionHandlerProvider;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class HelloExceptionHandlerProvider implements ExceptionHandlerProvider {

    @Inject
    protected HelloGrpcExceptionMapper mapper;

    @Override
    public <ReqT, RespT> ExceptionHandler<ReqT, RespT> createHandler(final ServerCall.Listener<ReqT> listener,
        final ServerCall<ReqT, RespT> serverCall, final Metadata metadata) {
        System.out.println("HelloExceptionHandlerProvider::createHandler");
        return new HelloExceptionHandler<>(listener, serverCall, metadata);
    }

    @Override
    public Throwable transform(final Throwable throwable) {
        var helloEx = toHelloException(throwable);
        var grpcEx = mapper.toGrpc(helloEx);
        System.out.println("HelloExceptionHandlerProvider::transform - Should exit with status " + grpcEx.getStatus().getCode().name());
        return grpcEx;
    }

    protected HelloException toHelloException(final Throwable throwable) {
        if (throwable instanceof HelloException) {
            return (HelloException) throwable;
        }
        return new HelloException("ERROR_CODE_UNKNOWN");
    }

    protected static class HelloExceptionHandler<I, O> extends ExceptionHandler<I, O> {

        public HelloExceptionHandler(final ServerCall.Listener<I> listener, final ServerCall<I, O> call,
            final Metadata metadata) {
            super(listener, call, metadata);
            System.out.println("HelloExceptionHandler::constructor");
        }

        @Override
        protected void handleException(final Throwable throwable, final ServerCall<I, O> call,
            final Metadata metadata) {
            // --------------------
            // This is never called
            // --------------------
            System.out.println("HelloExceptionHandler::handleException");
            StatusException ex = (StatusException) throwable;
            call.close(ex.getStatus(), ex.getTrailers());
        }
    }
}
