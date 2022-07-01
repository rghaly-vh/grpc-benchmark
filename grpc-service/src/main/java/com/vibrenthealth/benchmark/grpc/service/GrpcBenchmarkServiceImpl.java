package com.vibrenthealth.benchmark.grpc.service;

import com.vibrenthealth.benchmark.grpc.service.lib.DoSomethingReply;
import com.vibrenthealth.benchmark.grpc.service.lib.DoSomethingRequest;
import com.vibrenthealth.benchmark.grpc.service.lib.GrpcBenchmarkServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcBenchmarkServiceImpl  extends GrpcBenchmarkServiceGrpc.GrpcBenchmarkServiceImplBase {

    @Override
    public void doSomething(DoSomethingRequest request, StreamObserver<DoSomethingReply> responseObserver) {
        long sleepAmount = request.getMilliseconds();
        boolean result = true;
        long reqRec = System.currentTimeMillis();

        try {
            Thread.sleep(sleepAmount);
        } catch (InterruptedException e) {
            result = false;
        }

        DoSomethingReply reply = DoSomethingReply.newBuilder()
                .setTestId(request.getTestId())
                .setIterationId(request.getIterationId())
                .setRequestReceived(reqRec)
                .setResponseSent(System.currentTimeMillis())
                .setLocalDuration(System.currentTimeMillis() - reqRec)
                .setAllWas200(result)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }


}
