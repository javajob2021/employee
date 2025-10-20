import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <nav style="padding:8px;border-bottom:1px solid #eee">
      <a routerLink="/employees">Employees</a> |
      <a routerLink="/employees/new">New</a>
    </nav>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {}
