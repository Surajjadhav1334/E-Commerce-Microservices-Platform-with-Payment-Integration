import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {

  user = {
    username: '',
    password: ''
  };

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {
  this.authService.login(this.user).subscribe({
    next: (token: string) => {

      // 🔥 save token
      this.authService.saveToken(token);

      const role = this.authService.getRole();
      console.log("ROLE:", role);

      if (role === 'ROLE_ADMIN') {
        alert("Admin Login Successful");
        this.router.navigate(['/admin']);
      } else {
        alert("User Login Successful");
        this.router.navigate(['/user']);
      }
    },
    error: () => {
      alert("Invalid Credentials ❌");
    }
  });
}
}
