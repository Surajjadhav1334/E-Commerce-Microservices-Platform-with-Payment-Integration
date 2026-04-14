import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http : HttpClient) { }

   createPayment(data: any) {
    return this.http.post("http://localhost:8084/payment/create", data);
  }

    verifyPayment(orderId: string, paymentId: string) {
    return this.http.post(
      `http://localhost:8084/payment/verify?orderId=${orderId}&paymentId=${paymentId}`, {}
    );
  }
}

