package com.skillenza.parkinglotjava;


import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mockito.ArgumentMatchers;
import org.mockito.internal.matchers.Any;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class ParkinglotjavaApplicationTests {
	@Autowired
	private MockMvc mockmvc;
	
	@Autowired
	private ParkingLotController parkinLotController;
	
	@MockBean
	private ParkingLotRepository parkinrepo;
	
	
	@Test
	public void GetAllParkingInfo() throws Exception
	{
		ParkingLot lot= new ParkingLot(45,1,new Date(),60,55,5,new Date());
		List<ParkingLot> parkingList= new ArrayList<>();
		parkingList.add(lot);
		when(parkinrepo.findAll()).thenReturn(parkingList);
		this.mockmvc.perform(get("/api/parkings")).andExpect(status().isOk());
	}
	
	@Test
	public void PostParkingInfoWithCorrectValues() throws Exception
	{
		ParkingLot lot= new ParkingLot(45,1,new Date(),60,55,5,new Date());
		List<ParkingLot> parkingList= new ArrayList<>();
		parkingList.add(lot);
		//when(parkinrepo.save(any()));
		this.mockmvc.perform(MockMvcRequestBuilders.post("/api/parkings").content(new ObjectMapper().writeValueAsString(lot)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}
	
	@Test
	public void PostParkingInfoWithWrongValues() throws Exception
	{
		ParkingLot lot= new ParkingLot(0,1,null,60,55,5,new Date());
		List<ParkingLot> parkingList= new ArrayList<>();
		parkingList.add(lot);
		//when(parkinrepo.save(lot)).thenReturn(lot);
		this.mockmvc.perform(MockMvcRequestBuilders.post("/api/parkings").content(new ObjectMapper().writeValueAsString(lot)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}


}
