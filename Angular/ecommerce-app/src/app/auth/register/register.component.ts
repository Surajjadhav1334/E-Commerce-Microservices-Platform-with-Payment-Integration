import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true, // 👈 Jar he 'true' asel tar...
  imports: [FormsModule, HttpClientModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  user = {
    username: '',
    password: ''
  };

  constructor(private authService: AuthService, private router : Router) {}

  registerUser() {
    console.log("Sending Data:", this.user);
    this.authService.register(this.user).subscribe({
      next: (res) => {
        alert("User Registered");

        this.router.navigate(['/login'])
      },
       error: (err) => {
      console.log("ERROR:", err);  // 👈 debug
      alert(err.error);            // 👈 backend message show
    }
    });
  }

  registerAdmin() {
    this.authService.registerAdmin(this.user).subscribe({
      next: () => {
        alert("Admin Registered");
         this.router.navigate(['/login'])
      },
        error: (err) => {
      console.log("ERROR:", err);  // 👈 debug
      alert(err.error);            // 👈 backend message show
    }
    });
  }
}


