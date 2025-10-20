export interface Employee {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  title?: string;
  department?: string;
  hireDate?: string;   // yyyy-MM-dd
  salary?: number;
  status: 'ACTIVE' | 'INACTIVE' | 'ON_LEAVE' | 'TERMINATED';
  createdAt?: string;
  updatedAt?: string;
}
