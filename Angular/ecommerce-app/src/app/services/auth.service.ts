import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthResponse } from '../models/auth-response';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService { // 👈 Sagle logic ya class chya AAT pahije


  private baseurl = "http://localhost:8080/auth";

  constructor(private http: HttpClient) { }

 register(user:any) {
  return this.http.post(`${this.baseurl}/register`, user, {
    responseType: 'text'   // 👈 THIS IS THE FIX
  });
}

 registerAdmin(user:any) {
  return this.http.post(`${this.baseurl}/register-admin`, user, {
    responseType: 'text'
  });
}

login(user: any): Observable<string> {
  return this.http.post(`${this.baseurl}/login`, user, {
    responseType: 'text'
  });
}

  saveToken(token :string)
  {
    localStorage.setItem("token", token);
  }

  getToken()
  {
    return localStorage.getItem("token");
  }

  logout()
  {
    localStorage.removeItem("token");
  }

getRole(): string | null {
  const token = this.getToken();

  if (!token) return null;

  const decoded: any = jwtDecode(token);

  // 🔥 Multiple cases handle करा
  if (decoded.role) {
    return decoded.role;
  }

  if (decoded.authorities && decoded.authorities.length > 0) {
    return decoded.authorities[0];
  }

  return null;
}

getRoleFromToken(token: string): string {
  const decoded: any = jwtDecode(token);
  return decoded.role; // 👈 backend मध्ये role add केलं पाहिजे
}


} // 👈 Class ithe sampato
