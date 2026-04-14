import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service'; // 👈 Path check kara
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
})
export class NavbarComponent {

  // 1. Constructor madhe service inject kara
  constructor(public authService: AuthService, private router: Router) {}

  // 2. Role check karnyachi logic
  isAdmin() {
    return this.authService.getRole() === 'ROLE_ADMIN';
  }

  isUser() {
    return this.authService.getRole() === 'ROLE_USER';
  }

  // 3. Logout logic (Optional pan mhatvache)
  logout() {
    this.authService.logout(); // LocalStorage clear kara
    this.router.navigate(['/login']);
  }
}
