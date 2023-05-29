package com.wileyedge.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.State;

@Repository
public class StateDaoFileImpl implements StateDao {
	
	private String taxesFilePath;
	
	public StateDaoFileImpl() throws FileNotFoundException {
		this.taxesFilePath = "data/data/Taxes.txt";
	}

	@Override
	public List<State> getStates() throws FileNotFoundException {
		List<State> states = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new BufferedReader(new FileReader(taxesFilePath)))) {
			sc.nextLine();
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				String[] fields = currentLine.split(",");
				State state = new State(fields[0], fields[1], new BigDecimal(fields[2]));
				states.add(state);
			}			
		}
		
		return states;
	}

	public State getStateByAbbrev(String stateAbbrev) throws FileNotFoundException {
		List<State> states = getStates();
		Optional<State> state = states.stream().filter(s -> s.getStateAbbrev().equals(stateAbbrev)).findFirst();
		if (state.isPresent()) return state.get();
		return null;
	}
	
}
