package com.wileyedge.service;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wileyedge.dao.StateDao;
import com.wileyedge.model.State;

@Service
public class StateService {

	@Autowired
	StateDao dao;

	public State findState(String stateAbbrev) throws FileNotFoundException {
		return dao.getStateByAbbrev(stateAbbrev);
	}
	
	
}
