package com.errabi.ishop.grpc;

import com.errabi.ishop.GreeterServiceGrpc;
import com.errabi.ishop.HelloReply;
import com.errabi.ishop.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl extends GreeterServiceGrpc.GreeterServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        var response =  HelloReply.newBuilder().setMessage("Hello world").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
