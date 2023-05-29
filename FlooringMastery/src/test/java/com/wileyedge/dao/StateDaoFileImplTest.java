package com.wileyedge.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.wileyedge.model.State;

class StateDaoFileImplTest {

	private StateDaoFileImpl dao;
	
	public StateDaoFileImplTest() throws FileNotFoundException {
		this.dao = new StateDaoFileImpl("test-data/data/Taxes.txt");
	}

	@Test
	void getStatesReturnsStates() throws FileNotFoundException {
		State state = new State("KY", "Kentucky", new BigDecimal("6.00"));
	
		List<State> states = dao.getStates();
		
		assertEquals(states.size(), 4);
		assertTrue(states.contains(state));
	}
	
	@Test
	void getStateByAbbrevReturnsExpectedState() throws FileNotFoundException {
		State expectedState = new State("KY", "Kentucky", new BigDecimal("6.00"));
		
		State actualState = dao.getStateByAbbrev("KY");
		
		assertEquals(actualState, expectedState);
	}

	@Test
	void getStateByAbbrevReturnsNullIfStateNotFound() throws FileNotFoundException {
		State actualState = dao.getStateByAbbrev("NY");
		
		assertNull(actualState);
	}

}
