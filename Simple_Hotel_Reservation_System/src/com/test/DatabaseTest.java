package com.test;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import com.functions.mysql;
import com.gui.RegisterCustomer;
import com.functions.empPanelFunctions;

class DatabaseTest {
	mysql mysql = new mysql();
	empPanelFunctions EmployeePanelFunctions =new empPanelFunctions();
	
	
	
	
	@Test
	void testGetAvailableRooms() {
		String[] input = mysql.dbGetAvailableRooms("Athens","VIP",4,0,"02/10/2018","08/10/2018");
		String[] output = {"Room 2"};
		Assert.assertArrayEquals(output,input);	
	}	
	@Test
	 void testGetRoomTypes() {
		String[] input = mysql.dbGetRoomTypes("Athens");
		String[] output = {"VIP","simple"};
		Assert.assertArrayEquals(output,input);
	}
	@Test
	void testCreateHotel() {
		assertEquals(true,mysql.dbCreateHotel("NewHotelName"));
	}
	@Test
	void testRegisterCustomer() {
		RegisterCustomer RegisterCustomer = new RegisterCustomer("Athens", "1", "Room 1", "02/10/2018", "08/10/2018", 150);
		assertEquals(true,RegisterCustomer.reached);
	}
	
	@Test
	void testUserValidation() {
		try {
			assertEquals(true,mysql.dbEmployeeConnect("testManager",EmployeePanelFunctions.toMd5("111")));
		} catch (NoSuchAlgorithmException e) {
		}
	}	
	@Test
	void testGetBooks() {
		String[] input = mysql.dbGetBooks("Athens");
		String[] output = new String[0];
		Assert.assertArrayEquals(output,input);		
	}
}
