package com.skillenza.parkinglotjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skillenza.parkinglotjava.ParkingLot;
import com.skillenza.parkinglotjava.ParkingLotRepository;

import javax.validation.Valid;
import javax.xml.ws.Response;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ParkingLotController {

	// your code goes here

	@Autowired
	private ParkingLotRepository parkinrepo;

	@GetMapping("/parkings")
	public ResponseEntity<List<ParkingLot>> GetParkingLot() {
		List<ParkingLot> parkingRepoList = parkinrepo.findAll();
		return new ResponseEntity<List<ParkingLot>>(parkingRepoList, HttpStatus.OK);
	}

	@PostMapping("/parkings")
	public ResponseEntity<Object> PostParkingLot(@RequestBody ParkingLot parkingobj) {
		ValidateParkingObj(parkingobj);
		parkinrepo.save(parkingobj);
		return new ResponseEntity<Object>(parkingobj, HttpStatus.CREATED);

	}

	private void ValidateParkingObj(ParkingLot parkingobj) {
		List<ParkingLot> parkingRepoList = parkinrepo.findAll();
		ParkingLot lot1 = parkingRepoList.stream().filter(parkingLot -> (parkingLot.getLot() == parkingobj.getLot()))
				.findAny().orElse(null);
		if (parkingobj.getLot() == 0 || lot1 != null) {
			throw new ResourceNotFoundException("Lot", "ParkingLot", parkingobj.getLot());
		}
		ParkingLot lot2 = parkingRepoList.stream()
				.filter(parkingLot -> (parkingLot.getVehicleNumber() == parkingobj.getVehicleNumber())).findAny()
				.orElse(null);
		if (parkingobj.getVehicleNumber() == 0 || lot2 != null) {
			throw new ResourceNotFoundException("vehicleNumber", "ParkingLot", parkingobj.getVehicleNumber());
		}
		if (parkingobj.getParkingDuration() == 0) {
			throw new ResourceNotFoundException("Duration", "ParkingLot", parkingobj.getParkingDuration());
		}
		if (parkingobj.getParkingAmount() == 0) {
			throw new ResourceNotFoundException("ParkingAmount", "ParkingLot", parkingobj.getParkingAmount());
		}
	}

}
