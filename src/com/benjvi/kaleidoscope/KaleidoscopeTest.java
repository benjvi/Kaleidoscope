package com.benjvi.kaleidoscope;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class KaleidoscopeTest {

	@Test
	public void test() {
		Kaleidoscope kTest = new Kaleidoscope(500, 500);
		List<List<Triangle>> result = kTest.getReflectionsList();
		assertEquals(result.get(0).size(), 1);
		assertEquals(result.get(1).size(), 3);
	}

}
