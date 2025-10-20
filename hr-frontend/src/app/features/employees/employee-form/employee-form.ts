import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { EmployeeService } from '../employee.service';
import { Employee } from '../employee';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './employee-form.html',
  styleUrls: ['./employee-form.scss']
})
export class EmployeeFormComponent implements OnInit {
  model: Employee = { firstName: '', lastName: '', email: '', status: 'ACTIVE' };
  id?: number;
  isEdit = false;
  saving = false;
  error?: string;
  statuses = ['ACTIVE', 'INACTIVE', 'ON_LEAVE', 'TERMINATED'] as const;

  // ✅ Inject ChangeDetectorRef
  constructor(
    private api: EmployeeService,
    private ar: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const idParam = this.ar.snapshot.paramMap.get('id');
    if (idParam && idParam !== 'new') {
      this.id = +idParam;
      this.isEdit = true;

      this.api.get(this.id).subscribe({
        next: e => {
          this.model = e;
          this.cdr.detectChanges(); // ✅ ensure view updates
        },
        error: err => {
          this.error = err?.message ?? 'Load failed';
          this.cdr.detectChanges(); // ✅ ensure error message renders
        }
      });
    }
  }

  save(): void {
    this.saving = true;
    this.cdr.detectChanges(); // update "Saving..." button state if used

    const req$ = (this.isEdit && this.id)
      ? this.api.update(this.id, this.model)
      : this.api.create(this.model);

    req$.subscribe({
      next: () => {
        this.router.navigate(['/employees']);
      },
      error: err => {
        this.error = err?.message ?? 'Save failed';
        this.saving = false;
        this.cdr.detectChanges(); // ✅ force refresh on error
      }
    });
  }
}
