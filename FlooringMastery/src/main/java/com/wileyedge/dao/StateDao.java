package com.wileyedge.dao;

import java.io.FileNotFoundException;
import java.util.List;

import com.wileyedge.model.State;

public interface StateDao {
	
	List<State> getStates() throws FileNotFoundException;
	State getStateByAbbrev(String stateAbbrev) throws FileNotFoundException;
	
}
