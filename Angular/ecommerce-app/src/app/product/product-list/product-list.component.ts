import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service'; // ✔️ path check कर

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent {

  constructor(public authService: AuthService) {}

  isAdmin() {
    return this.authService.getRole() === 'ROLE_ADMIN';
  }

  isUser() {
    return this.authService.getRole() === 'ROLE_USER';
  }
}
