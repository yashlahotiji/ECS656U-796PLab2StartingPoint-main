package com.example.grpc.client.grpcclient;

import java.util.concurrent.CompletableFuture;

import com.example.grpc.server.grpcserver.MatrixRequest;
import com.example.grpc.server.grpcserver.MatrixServiceGrpc;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class calcumat {

    @Async("executor")
    public CompletableFuture<String> matrixCalc(String matrix, String matrixB, MatrixServiceGrpc.MatrixServiceBlockingStub stub)
    {
        return CompletableFuture.completedFuture(stub.multiplyBlock(MatrixRequest.newBuilder().setA(matrix).setB(matrixB).build()).getC());
    //to indicate that multiply can be completed in future, so no waiting for that particular function
    }

    @Async("executor")
    public CompletableFuture<String> matrixAdd(String matrix, String matrixB, MatrixServiceGrpc.MatrixServiceBlockingStub stub)
    {
        return CompletableFuture.completedFuture(stub.addBlock(MatrixRequest.newBuilder().setA(matrix).setB(matrixB).build()).getC());
    }
    
}
