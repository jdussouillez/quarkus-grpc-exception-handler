package io.quarkus.grpc.examples.hello;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.StatusException;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloGrpcExceptionMapper {

    public StatusException toGrpc(final HelloException ex) {
        var code = Code.UNKNOWN;
        if (ex.getCode().equals("ERROR_CODE_INTERNAL")) {
            code = code.INTERNAL;
        }
        var status = Status.fromCode(code)
            .withDescription(ex.getMessage())
            .withCause(ex);
        var metadata = new Metadata();
        metadata.put(Metadata.Key.of("hello-error-code", Metadata.ASCII_STRING_MARSHALLER), ex.getCode());
        return status.asException(metadata);
    }
}
