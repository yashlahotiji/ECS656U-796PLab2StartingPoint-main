package com.example.grpc.client.grpcclient;

import com.example.grpc.server.grpcserver.PingRequest;
import com.example.grpc.server.grpcserver.PongResponse;
import com.example.grpc.server.grpcserver.PingPongServiceGrpc;
import com.example.grpc.server.grpcserver.MatrixRequest;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.grpc.server.grpcserver.MatrixReply;
import com.example.grpc.server.grpcserver.MatrixServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
@Service
public class GRPCClientService {

	@Autowired
	calcumat matcal;

    public String ping() {
        	ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();        
		PingPongServiceGrpc.PingPongServiceBlockingStub stub
                = PingPongServiceGrpc.newBlockingStub(ch);        
		PongResponse helloResponse = stub.ping(PingRequest.newBuilder()
                .setPing("")
                .build());        
		ch.shutdown();        
		return helloResponse.getPong();
    }
    MatrixServiceGrpc.MatrixServiceBlockingStub[] stubset = new MatrixServiceGrpc.MatrixServiceBlockingStub[8];
	int stubnum = 0;
	public int[][] mult(int[][] matone, int[][] mattwo, int deadline) throws InterruptedException, ExecutionException {
		ManagedChannel ch1 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel ch2 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel ch3 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel ch4 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel ch5 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel ch6 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel ch7 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		ManagedChannel ch8 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
		int QUE = matone.length;
		int block = QUE/2;
		int[][] A = new int[block][block];
    	int[][] B = new int[block][block];
    	int[][] C = new int[block][block];
    	int[][] D = new int[block][block];
		int[][] Aa = new int[block][block];
		int[][] Ab = new int[block][block];
		int[][] Ba = new int[block][block];
		int[][] Bb = new int[block][block];
		int[][] Ca = new int[block][block];
		int[][] Cb = new int[block][block];
		int[][] Da = new int[block][block];
		int[][] Db = new int[block][block];
		for (int p = 0; p < block; p++) 
        { 
            for (int q = 0; q < block; q++)
            {
                Aa[p][q]=matone[p][q];
                Ab[p][q]=mattwo[p][q];
            }
        }
    	for (int p = 0; p < block; p++) 
        { 
            for (int q = block; q < QUE; q++)
            {
                Ba[p][q-block]=matone[p][q];
                Bb[p][q-block]=mattwo[p][q];
            }
        }
    	for (int p = block; p < QUE; p++) 
        { 
            for (int q = 0; q < block; q++)
            {
                Ca[p-block][q]=matone[p][q];
                Cb[p-block][q]=mattwo[p][q];
            }
        } 
    	for (int p = block; p < QUE; p++) 
        { 
            for (int q = block; q < QUE; q++)
            {
                Da[p-block][q-block]=matone[p][q];
                Db[p-block][q-block]=mattwo[p][q];
            }
        }
		MatrixServiceGrpc.MatrixServiceBlockingStub stub1 =MatrixServiceGrpc.newBlockingStub(ch1);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub2 =MatrixServiceGrpc.newBlockingStub(ch2);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub3 =MatrixServiceGrpc.newBlockingStub(ch3);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub4 =MatrixServiceGrpc.newBlockingStub(ch4);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub5 =MatrixServiceGrpc.newBlockingStub(ch5);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub6 =MatrixServiceGrpc.newBlockingStub(ch6);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub7 =MatrixServiceGrpc.newBlockingStub(ch7);
		MatrixServiceGrpc.MatrixServiceBlockingStub stub8 =MatrixServiceGrpc.newBlockingStub(ch8);
		long start = System.currentTimeMillis(); 
		String AP1 = stub1.multiplyBlock(MatrixRequest.newBuilder().setA(Arrays.deepToString(Aa)).setB(Arrays.deepToString(Ab)).build()).getC();
		long stop = System.currentTimeMillis();
		long footprint = stop - start; //timetaken for just 1 block of multiplication, so that we can find time reqd for entire operation
		int serversneeded = (int) Math.ceil( (footprint*12)/deadline);
		serversneeded = serversneeded > 8 ? 8 : serversneeded;
		
		stubset[0] = stub1;
		stubset[1] = stub2;
		stubset[2] = stub3;
		stubset[3] = stub4;
		stubset[4] = stub5;
		stubset[5] = stub6;
		stubset[6] = stub7;
		stubset[7] = stub8;
		int a= 0;
		CompletableFuture<String> APART1 = matcal.matrixCalc(Arrays.deepToString(Aa), Arrays.deepToString(Ab), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> APART2 = matcal.matrixCalc(Arrays.deepToString(Ba), Arrays.deepToString(Cb), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> BPART1 = matcal.matrixCalc(Arrays.deepToString(Aa), Arrays.deepToString(Bb), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> BPART2 = matcal.matrixCalc(Arrays.deepToString(Ba), Arrays.deepToString(Db), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> CPART1 = matcal.matrixCalc(Arrays.deepToString(Ca), Arrays.deepToString(Ab), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> CPART2 = matcal.matrixCalc(Arrays.deepToString(Da), Arrays.deepToString(Cb), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> DPART1 = matcal.matrixCalc(Arrays.deepToString(Ca), Arrays.deepToString(Bb), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> DPART2 = matcal.matrixCalc(Arrays.deepToString(Da), Arrays.deepToString(Db), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> APART3 = matcal.matrixAdd(APART1.get(), APART2.get(), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> BPART3 = matcal.matrixAdd(BPART1.get(), BPART2.get(), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> CPART3 = matcal.matrixAdd(CPART1.get(), CPART2.get(), stubset[a<serversneeded ? a++ : 0]);
		CompletableFuture<String> DPART3 = matcal.matrixAdd(DPART1.get(), DPART2.get(), stubset[a<serversneeded ? a++ : 0]);
		
		A = stringToDeep(APART3.get());
		B = stringToDeep(BPART3.get());
		C = stringToDeep(CPART3.get());
		D = stringToDeep(DPART3.get());
		
		int[][] pri = new int[QUE][QUE];

		for (int p = block; p < QUE; p++) 
        { 
            for (int q = 0; q < block; q++)
            {
                pri[p][q]=C[p-block][q];
            }
        } 

		for (int p = 0; p < block; p++) 
        { 
            for (int q = 0; q < block; q++)
            {
                pri[p][q]=A[p][q];
            }
        }

    	for (int p = 0; p < block; p++) 
        { 
            for (int q = block; q < QUE; q++)
            {
                pri[p][q]=B[p][q-block];
            }
        }

		for (int p = block; p < QUE; p++) 
        { 
            for (int q = block; q < QUE; q++)
            {
                pri[p][q]=D[p-block][q-block];
            }
        } 

    	for (int p=0; p<QUE; p++)
    	{
    		for (int q=0; q<QUE;q++)
    		{
    			System.out.print(pri[p][q]+" ");
    		}
    		System.out.println("");
    	}

		ch1.shutdown();
		ch2.shutdown();
		ch3.shutdown();
		ch4.shutdown();
		ch5.shutdown();
		ch6.shutdown();
		ch7.shutdown();
		ch8.shutdown();
    	return pri;
	
}
//String to Matrix
private static int[][] stringToDeep(String s) {
	int row = 0;
	int column = 0;
	for (int p = 0; p < s.length(); p++) {
		if (s.charAt(p) == '[') {
			row++;
		}
	}
	row--;
	for (int p = 0;; p++) {
		if (s.charAt(p) == ',') {
			column++;
		}
		if (s.charAt(p) == ']') {
			break;
		}
	}
	column++;
	int[][] mat = new int[row][column];
	s = s.replaceAll("\\[", "").replaceAll("\\]", "");
	String[] s1 = s.split(", ");
	int q = -1;
	for (int p = 0; p < s1.length; p++) {
		if (p % column == 0) {
			q++;
		}
		mat[q][p % column] = Integer.parseInt(s1[p]);
	}
	return mat;
}
}
