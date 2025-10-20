import { Routes } from '@angular/router';
import { EmployeesListComponent } from './features/employees/employees-list/employees-list';
import { EmployeeFormComponent } from './features/employees/employee-form/employee-form';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'employees' },
  { path: 'employees', component: EmployeesListComponent },
  { path: 'employees/new', component: EmployeeFormComponent },
  { path: 'employees/:id', component: EmployeeFormComponent }
];
