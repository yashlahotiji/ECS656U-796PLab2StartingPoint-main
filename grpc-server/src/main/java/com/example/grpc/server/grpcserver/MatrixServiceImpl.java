package com.example.grpc.server.grpcserver;


import java.util.Arrays;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
@GrpcService
public class MatrixServiceImpl extends MatrixServiceGrpc.MatrixServiceImplBase
{
	@Override
	public void addBlock(MatrixRequest request, StreamObserver<MatrixReply> reply)
	{
		// System.out.println("Request received from client:\n" + request);
		// int C00=request.getA00()+request.getB00();
    	// 	int C01=request.getA01()+request.getB01();
		// int C10=request.getA10()+request.getB10();
		// int C11=request.getA11()+request.getB11();
		// MatrixReply response = MatrixReply.newBuilder().setC00(C00).setC01(C01).setC10(C10).setC11(C11).build();
		// reply.onNext(response);
		// reply.onCompleted();
		String a = request.getA();
		String b = request.getB();
		int[][] matra = stringToDeep(a);
		int[][] matrb = stringToDeep(b);
		int[][] matrc = new int[matra.length][matra[0].length];
		int i,j;
		for(i=0;i<matra.length;i++){
			for(j=0;j<matrb.length;j++){
					matrc[i][j] = matra[i][j]+matrb[i][j];
			}
		}
		MatrixReply response = MatrixReply.newBuilder().setC(Arrays.deepToString(matrc)).build();
		reply.onNext(response);
		reply.onCompleted();
    }
	@Override
    	public void multiplyBlock(MatrixRequest request, StreamObserver<MatrixReply> reply)
    	{
        // 	System.out.println("Request received from client:\n" + request);
        // 	int C00=request.getA00()*request.getB00()+request.getA01()*request.getB10();
		// int C01=request.getA00()*request.getB01()+request.getA01()*request.getB11();
		// int C10=request.getA10()*request.getB00()+request.getA11()*request.getB10();
		// int C11=request.getA10()*request.getB01()+request.getA11()*request.getB11();
        // MatrixReply response = MatrixReply.newBuilder().setC00(C00).setC01(C01).setC10(C10).setC11(C11).build();
        // reply.onNext(response);
        // reply.onCompleted();
		String a = request.getA();
		String b = request.getB();
		int[][] matra = stringToDeep(a);
		int[][] matrb = stringToDeep(b);
		int[][] matrc = new int[matra.length][matra[0].length];
		int i,j,k;
		for(i=0;i<matra.length;i++){
			for(j=0;j<matrb.length;j++){
				for(k=0;k<matrb.length;k++){
					matrc[i][j]+= matra[i][k]*matrb[k][j];
				}
			}
		}
		MatrixReply response = MatrixReply.newBuilder().setC(Arrays.deepToString(matrc)).build();
		reply.onNext(response);
		reply.onCompleted();
    }
	//String to Matrix
	private static int[][] stringToDeep(String str) {
		int row = 0;
		int col = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '[') {
				row++;
			}
		}
		row--;
		for (int i = 0;; i++) {
			if (str.charAt(i) == ',') {
				col++;
			}
			if (str.charAt(i) == ']') {
				break;
			}
		}
		col++;
		int[][] out = new int[row][col];
		str = str.replaceAll("\\[", "").replaceAll("\\]", "");
		String[] s1 = str.split(", ");
		int j = -1;
		for (int i = 0; i < s1.length; i++) {
			if (i % col == 0) {
				j++;
			}
			out[j][i % col] = Integer.parseInt(s1[i]);
		}
		return out;
	}
}
