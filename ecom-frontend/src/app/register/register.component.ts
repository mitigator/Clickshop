import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../_services/user.service';
import { ThemeService } from '../_services/theme.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  hidePassword = true;
  loading = false;
  isDarkTheme = false;

  constructor(
    private userService: UserService,
    private router: Router,
    private themeService: ThemeService
  ) { }

  ngOnInit(): void {
    // Subscribe to theme changes
    this.themeService.isDarkTheme.subscribe(isDark => {
      this.isDarkTheme = isDark;
    });
  }

  toggleTheme(): void {
    this.themeService.toggleDarkTheme();
  }

  register(registerForm: NgForm) {
    if (!registerForm.valid) {
      alert('Please fill in all required fields and agree to the terms');
      return;
    }

    this.loading = true;
    
    this.userService.register(registerForm.value).subscribe(
      (response) => {
        this.router.navigate(['/login']);
      },
      (error) => {
        console.log(error);
        alert(error?.error?.message || 'Registration failed. Please try again.');
        this.loading = false;
      },
      () => {
        this.loading = false;
      }
    );  
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }
}