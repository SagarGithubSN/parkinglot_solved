import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs";

class ParkingInfo {
  id: number;
  lot: number;
  vehicleNumber: number;
  parkingDuration: number;
  parkingAmount: number

}
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  vehicleForm: FormGroup

  parkings: ParkingInfo[];
  errorAlert: Boolean;

  error_message: String;
  // your code goes here
  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.vehicleForm = new FormGroup({
      vehicleLot: new FormControl(),
      vehicleNumber: new FormControl(),
      vehicleDuration: new FormControl(),
      vehicleAmount: new FormControl()
    });
  }

  async onSubmit() {
    this.errorAlert = false;

    const { vehicleForm } = this;

    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/json; charset=utf-8',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET,POST,PATCH,DELETE,PUT,OPTIONS',
      'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token, content-type',
      'Authorization': 'my-auth-token'
    });
    let options = {
      headers: httpHeaders
    };

    let parkingInfo = {
      lot: vehicleForm.value.vehicleLot,
      vehicleNumber: vehicleForm.value.vehicleNumber,
      parkingDuration: vehicleForm.value.vehicleDuration,
      parkingAmount: vehicleForm.value.vehicleAmount
    }
    let errMessage;
    this.http.get<ParkingInfo[]>("http://localhost:8080/api/parkings", options).subscribe(async (res) => {
      res.forEach((result) => {
        if (result.lot === parkingInfo.lot || result.vehicleNumber === parkingInfo.vehicleNumber) {
          errMessage = 'Vehicle Already Parked';
          this.errorAlert = true;

          this.error_message = errMessage;
        }
      });
      if (errMessage === null || errMessage === undefined) {
        this.http.post("http://localhost:8080/api/parkings", parkingInfo, options).subscribe(async (res) => {
          this.http.get<ParkingInfo[]>("http://localhost:8080/api/parkings", options).subscribe(async (res) => {
            this.parkings = res;
          });
          console.log(this.parkings)
        }, (error: HttpErrorResponse) => {
          this.errorAlert = true;

          this.error_message = error.error.message;
        }
        )
      }

    });
  }

  calculateAmount(event: any) {
    const vehicleDuration = this.vehicleForm.value.vehicleDuration;
    let vehicleAmount;
    if (vehicleDuration < 60) {
      vehicleAmount = 20;
    }
    else if (vehicleDuration > 60) {
      const residualmin = vehicleDuration - 60;
      const resAmt = residualmin * 0.333;
      vehicleAmount = resAmt + 20;
    }
    vehicleAmount = Math.round(vehicleAmount);
    this.vehicleForm.get('vehicleAmount').setValue(vehicleAmount);
  }

}
