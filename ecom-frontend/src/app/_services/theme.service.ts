// theme.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private darkThemeSubject = new BehaviorSubject<boolean>(false);
  isDarkTheme = this.darkThemeSubject.asObservable();

  constructor() {
    // Check for saved theme preference
    const savedTheme = localStorage.getItem('isDarkTheme');
    if (savedTheme) {
      this.setDarkTheme(savedTheme === 'true');
    }
  }

  setDarkTheme(isDarkTheme: boolean): void {
    this.darkThemeSubject.next(isDarkTheme);
    localStorage.setItem('isDarkTheme', isDarkTheme.toString());
    
    // Apply theme to document body
    if (isDarkTheme) {
      document.body.classList.add('dark-theme');
    } else {
      document.body.classList.remove('dark-theme');
    }
  }

  toggleDarkTheme(): void {
    this.setDarkTheme(!this.darkThemeSubject.value);
  }
}