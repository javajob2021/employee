import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map, tap } from 'rxjs';
import { Employee } from './employee';

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private readonly baseUrl = '/api/employees'; // proxied to backend (8081 or 8082)

  constructor(private http: HttpClient) {}

  /**
   * Fetch all employees.
   * Backend returns Page<Employee>, but we only need the content array.
   */
  list(): Observable<Employee[]> {
    return this.http.get<{ content: Employee[] }>(this.baseUrl).pipe(
      map(res => res.content ?? []),
      tap({
        next: data => console.log(`✅ Loaded ${data.length} employees`),
        error: err => console.error('❌ Error loading employees:', err)
      })
    );
  }

  /** Get a single employee by ID */
  get(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${this.baseUrl}/${id}`);
  }

  /** Create a new employee */
  create(e: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.baseUrl, e);
  }

  /** Update an existing employee */
  update(id: number, e: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.baseUrl}/${id}`, e);
  }

  /** Delete employee by ID */
  remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
