package com.skillenza.parkinglotjava;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "parkinglots")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class ParkingLot {
 
@Id @GeneratedValue(strategy=GenerationType.AUTO)
@NotNull
private long id;

@NotNull
@Temporal(TemporalType.TIMESTAMP)
@LastModifiedDate
private Date createdAt;

@Column(unique=true)
@NotNull

private int lot;

@NotNull
private int parkingAmount;

@NotNull
private int parkingDuration;

@NotNull
@Temporal(TemporalType.TIMESTAMP)
@LastModifiedDate
private Date updatedAt;

@Column(unique=true)
@NotNull
private int vehicleNumber;

public ParkingLot(@NotNull int parkingAmount, @NotNull long id, @NotNull Date createdAt, @NotNull int parkingDuration,
		@NotNull int vehicleNumber, @NotNull int lot, @NotNull Date updatedAt) {

	this.parkingAmount = parkingAmount;
	this.id = id;
	this.createdAt = createdAt;
	this.parkingDuration = parkingDuration;
	this.vehicleNumber = vehicleNumber;
	this.lot = lot;
	this.updatedAt = updatedAt;
}
public ParkingLot() {
	
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public Date getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
}
public int getLot() {
	return lot;
}
public void setLot(int lot) {
	this.lot = lot;
}
public int getParkingAmount() {
	return parkingAmount;
}
public void setParkingAmount(int parkingAmount) {
	this.parkingAmount = parkingAmount;
}
public int getParkingDuration() {
	return parkingDuration;
}
public void setParkingDuration(int parkingDuration) {
	this.parkingDuration = parkingDuration;
}
public Date getUpdatedAt() {
	return updatedAt;
}
public void setUpdatedAt(Date updatedAt) {
	this.updatedAt = updatedAt;
}
public int getVehicleNumber() {
	return vehicleNumber;
}
public void setVehicleNumber(int vehicleNumber) {
	this.vehicleNumber = vehicleNumber;
}
    
}

