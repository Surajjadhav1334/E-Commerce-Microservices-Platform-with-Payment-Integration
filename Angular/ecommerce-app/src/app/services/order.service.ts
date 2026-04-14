import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http : HttpClient) { }

    private getHeaders() {
      return {
        headers: new HttpHeaders({
          Authorization: `Bearer ${localStorage.getItem('token')}`
        })
      };
    }

    getOrders() {
      return this.http.get("http://localhost:8083/orders/all", this.getHeaders());
    }

    getOrderbyId(id : number) : Observable<any> {
      return this.http.get(`http://localhost:8083/orders/${id}`, this.getHeaders());
    }

    addOrders(p: any) {
      return this.http.post("http://localhost:8083/orders/create", p, this.getHeaders());
    }

    updateOrder(id: number, p: any) {
      return this.http.put(`http://localhost:8083/orders/update/${id}`, p, this.getHeaders());
    }

    deleteOrder(id: number) {
      return this.http.delete(`http://localhost:8083/orders/delete/${id}`, {...this.getHeaders(), responseType: 'text' });
    }

     createPayment(order: any) {
    return this.http.post("http://localhost:8084/payment/create", order);
  }

  verifyPayment(data: any) {
     return this.http.post(`http://localhost:8084/payment/verify?orderId=${data.orderId}&razorpayPaymentId=${data.razorpayPaymentId}`, data);
  }
}
