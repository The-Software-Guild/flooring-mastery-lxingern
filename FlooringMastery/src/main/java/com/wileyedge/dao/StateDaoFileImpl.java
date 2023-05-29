package com.wileyedge.dao;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.wileyedge.model.State;

@Repository
public class StateDaoFileImpl extends DaoFileImpl implements StateDao {
	
	private String taxesFilePath;
	
	public StateDaoFileImpl() throws FileNotFoundException {
		this.taxesFilePath = "data/data/Taxes.txt";
	}

	public StateDaoFileImpl(String taxesFilePath) {
		this.taxesFilePath = taxesFilePath;
	}

	@Override
	public List<State> getStates() throws FileNotFoundException {
		return loadListFromFile(taxesFilePath, ",", (fields) -> {
			return new State(fields[0], fields[1], new BigDecimal(fields[2]));
		});	
	}

	public State getStateByAbbrev(String stateAbbrev) throws FileNotFoundException {
		List<State> states = getStates();
		Optional<State> state = states.stream().filter(s -> s.getStateAbbrev().equals(stateAbbrev)).findFirst();
		if (state.isPresent()) return state.get();
		return null;
	}
	
}
