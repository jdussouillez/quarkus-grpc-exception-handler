package io.quarkus.grpc.examples.hello;

public class HelloException extends RuntimeException {

    protected final String code;

    public HelloException(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
