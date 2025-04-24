// login.component.ts
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';
import { ThemeService } from '../_services/theme.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  hidePassword = true;
  loading = false;
  isDarkTheme = false;

  constructor(
    private userService: UserService,
    private userAuthService: UserAuthService,
    private router: Router,
    private themeService: ThemeService
  ) {}

  ngOnInit(): void {
    // Redirect if already logged in
    if (this.userAuthService.isLoggedIn()) {
      this.router.navigate(['/']);
    }
    
    // Subscribe to theme changes
    this.themeService.isDarkTheme.subscribe(isDark => {
      this.isDarkTheme = isDark;
    });
  }

  toggleTheme(): void {
    this.themeService.toggleDarkTheme();
  }

  login(loginForm: NgForm) {
    if (!loginForm.valid) {
      alert('Please fill in all required fields');
      return;
    }
    
    this.loading = true;
    
    this.userService.login(loginForm.value).subscribe(
      (response: any) => {
        this.userAuthService.setRoles(response.user.role);
        this.userAuthService.setToken(response.jwtToken);

        const role = response.user.role[0].roleName;
        
        if (role === 'Admin') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/user']);
        }
      },
      (error) => {
        console.log(error);
        this.loading = false;
        
        alert(error?.error?.message || 'Login failed. Please check your credentials.');
      },
      () => {
        this.loading = false;
      }
    );
  }

  registerUser() {
    this.router.navigate(['/register']);
  }
}