import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { EmployeeService } from '../employee.service';
import { Employee } from '../employee';
import { ChangeDetectorRef } from '@angular/core';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-employees-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './employees-list.html',
  styleUrls: ['./employees-list.scss']
})
export class EmployeesListComponent implements OnInit {
  employees: Employee[] = [];
  loading = false;
  error?: string;

  constructor(private api: EmployeeService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
  this.loading = true;

  this.api.list()
    .pipe(finalize(() => {
      this.loading = false;
      this.cdr.detectChanges();   // manually update view
    }))
    .subscribe({
      next: (data: Employee[]) => {
        this.employees = data;
      },
      error: err => {
        this.error = err?.message ?? 'Load failed';
      }
    });
}

  delete(id?: number): void {
    if (!id) return;
    if (!confirm('Delete this employee?')) return;
    this.api.remove(id).subscribe({
      next: () => this.load(),
      error: err => {
        console.error('Delete failed:', err);
        this.error = 'Delete failed';
      }
    });
  }
}
