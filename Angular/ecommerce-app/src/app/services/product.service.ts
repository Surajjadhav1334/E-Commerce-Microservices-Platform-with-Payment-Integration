import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http : HttpClient) { }

    private getHeaders() {
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('token')}`
      })
    };
  }

 getProducts() {
  return this.http.get("http://localhost:8082/products/all", this.getHeaders());
}

getProductbyId(id : number) : Observable<any> {
  return this.http.get(`http://localhost:8082/products/${id}`, this.getHeaders());
}

addProduct(p: any) {
  return this.http.post("http://localhost:8082/products/add", p, this.getHeaders());
}

updateProduct(id: number, p: any) {
  return this.http.put(`http://localhost:8082/products/update/${id}`, p, this.getHeaders());
}

deleteProduct(id: number) {
  return this.http.delete(`http://localhost:8082/products/${id}`, {...this.getHeaders(), responseType: 'text' });
}

}
