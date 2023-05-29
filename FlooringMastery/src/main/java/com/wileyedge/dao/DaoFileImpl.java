package com.wileyedge.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class DaoFileImpl {

	public <T> List<T> loadListFromFile(String filePath, String delimiter, Function<String[], T> mapper) throws FileNotFoundException {
		List<T> list = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(filePath)))) {
			sc.nextLine();
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				String[] fields = currentLine.split(delimiter);
				
				T newObj = mapper.apply(fields);
	
				list.add(newObj);
			}			
		}

		return list;
	}
}
