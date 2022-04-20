package com.example.grpc.client.grpcclient;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class PingPongEndpoint
{    
	GRPCClientService grpcClientService;    
	@Autowired
    	public PingPongEndpoint(GRPCClientService grpcClientService) {
        	this.grpcClientService = grpcClientService;
    	}    
	@GetMapping("/ping")
    	public String ping() {
        	return grpcClientService.ping();
    	}
    //     @GetMapping("/add")
	// public String add() {
	// 	return grpcClientService.add();
	//}
	@PostMapping("/upmul")
	public String Mul(@RequestParam("fileone") MultipartFile matfile,@RequestParam("filetwo") MultipartFile matfiletwo,@RequestParam int deadline) throws IOException, InterruptedException, ExecutionException {
		//convert into matrix
		List <String> listone = new ArrayList<>();
		List <String> listtwo = new ArrayList<>();
		//InputStreamReader ir = new InputStreamReader(System.in);
		//For Matrix 1
		InputStream isr1 = matfile.getInputStream();
		BufferedReader br1 = new BufferedReader(new InputStreamReader(isr1));
		//For Matrix 2
		InputStream isr2 = matfiletwo.getInputStream();
		BufferedReader br2 = new BufferedReader(new InputStreamReader(isr2));

		listone = br1.lines().collect(Collectors.toList());
		listtwo = br2.lines().collect(Collectors.toList());
		int [][] matrixone = getMatrix(listone);
		int [][] matrixtwo = getMatrix(listtwo);
		int [][] finalmat = grpcClientService.mult(matrixone,matrixtwo,deadline);
		return Arrays.deepToString(finalmat);
	}
	
	//created a matrix
	public int[][] getMatrix(List<String> listone) {
		int row = listone.size();
		int column = listone.get(0).split( " ").length;
		int [][] mat = new int [row][column];
		int p,q;
		for(p=0;p<row;p++){
			String arr[]=listone.get(p).split(" ");
			for(q=0;q<column;q++){
				mat [p][q] = Integer.parseInt(arr[q]);
			}
		}
		return mat;

	}

}
