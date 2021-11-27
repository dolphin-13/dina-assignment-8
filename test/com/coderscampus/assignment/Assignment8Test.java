package com.coderscampus.assignment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.junit.jupiter.api.Test;


class Assignment8Test {

	@Test
	public void get_1000000_numbers() {
		Assignment8 assignment = new Assignment8();
		String message = "Starting";
		System.out.println(message);
		System.out.println("Thread-"+Thread.currentThread().getName());
		
		List<CompletableFuture<Void>> tasks = new ArrayList<>();
		
		List<Integer> numbers = new ArrayList<>();
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		List<Integer> numbersList = assignment.getNumbers();
		
		for(int i=0; i< 1000; i++) {
			CompletableFuture<Void> task = 
					CompletableFuture.supplyAsync(() -> new Assignment8(), executor)
					                 .thenApply(a -> numbersList)
					                 .thenAccept(num -> numbers.addAll(num));
			tasks.add(task);
		}
		message = "Done";
		System.out.println(message);
				
		while(tasks.stream()
		   .filter(CompletableFuture::isDone)
		   .count() < 1000) 
			
			System.out.println(numbers.size() + " numbers added to the list");
		System.out.println(Arrays.toString(numbers.toArray()));
		assertEquals(1000000, numbers.size());
		
	  
	}	
}
