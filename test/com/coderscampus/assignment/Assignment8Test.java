package com.coderscampus.assignment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;


class Assignment8Test {

	@Test
	public void get_list_of_1000000_numbers_and_number_occurrences() {
		Assignment8 assignment = new Assignment8();
		String message = "Starting";
		System.out.println(message);		
		
		List<CompletableFuture<Void>> tasks = new ArrayList<>(1000);
		
		List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());
		
	//	ExecutorService executor = Executors.newFixedThreadPool(2);
		ExecutorService executor = Executors.newCachedThreadPool();
		
	//	List<Integer> numbersList = assignment.getNumbers();
		
		for(int i=0; i< 1000; i++) {
			CompletableFuture<Void> task = 
					CompletableFuture.supplyAsync(() -> assignment.getNumbers(), executor)
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
	    		
//		Map<Integer, Long> numberOccurrences = numbers.stream()
//				   .collect(Collectors.groupingBy(number -> number, Collectors.counting()));
			
//			numberOccurrences.forEach((number, occurrences) -> {
//				System.out.print(number + "=" + occurrences + ", ");
//			}); 

	       Map<Integer, Long> count = numbers.stream()
	                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

	        System.out.println(count);
	        
	        //verify the occurrences in another way 
	        Set<Integer> ns = new HashSet<>(numbers);
		     for (Integer n : ns)
		            System.out.println(n + "= " + Collections.frequency(numbers, n));	  
	}	
}
