package com.iron_bank.service.impl.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class IronBankServiceTest {

	private static IronBankServiceTest service;

	@BeforeAll
	public static void instantiateService() {
		System.out.println("First");
		service = new IronBankServiceTest();
	}

	@Test
	public void testLogin() {
		
	}

	@Test
	public void testZero() {
		System.out.println("Test zero");

	}

	@AfterAll
	public static void afterAll() {
		System.out.println("Last");
		service = null;
	}

}