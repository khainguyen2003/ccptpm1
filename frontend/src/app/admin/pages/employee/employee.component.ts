import { Component, OnInit } from '@angular/core';
import { Department } from 'src/app/models/Department';
import { Position } from 'src/app/models/Position';
import { FAKE_DEPARTMENT, FAKE_EMPLOYEE, FAKE_POSITION } from './fake-data';
import { FormGroup } from '@angular/forms';
import { MenuItem, MessageService } from 'primeng/api';
import { Employee } from 'src/app/models/employee';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.scss'
})
export class EmployeeComponent implements OnInit {

  optDepartment: Department[] = FAKE_DEPARTMENT;
  optPosition: Position[] = FAKE_POSITION;
  importOptions: MenuItem[] | undefined;
  employeeDialogVisible: boolean = false;
  deleteEmployeeDialogVisible: boolean = false;
  importFileDialogVisible: boolean = false;
  deleteEmployeesDialogVisible: boolean = false;
  urlUpload = "http://localhost:8080/admin/api/employees/file-upload";
  employees: Employee[];
  selectedEmployees: any = [];
  employee: any;
  rowsPerPageOptions = [5, 10, 20];
  errorMessage = '';
  headerEmployeeDialog: any;
  addForm: FormGroup;
  submited: boolean = false;
  uploadedFiles: any = [];
  page: number = 0;
  sort = 'id:desc';
  size: 5 | 10 | 20 = 5;
  search: string;

  constructor(
    private employeeService: EmployeeService,
    private message: MessageService,
  ) {}

  ngOnInit() {
    this.addForm = new FormGroup({

    });

    this.importOptions = [
        {
            label: 'Json',
            icon: 'pi pi-file',
            command: () => {
                this.showImportFileDialog();
            },
        },
        {
            label: 'Excel',
            icon: 'pi pi-file-excel',
            command: () => {
                this.showImportFileDialog();
            },
        },
    ];

    this.loadEmployees();

  }

  loadEmployees() {
    this.employeeService.getEmployees({
      search: this.search,
      position: 0,
      departmant: 0,
      page: this.page, 
      size: this.size, 
      sort: this.sort}).subscribe({
        next: res => {
          console.log(res);
          this.employees = res.employees;
        },
        error: err => {
          console.log(err);
          this.message.add({severity: 'error', detail: 'Không lấy được dữ liệu!'});
          this.message.add({severity: 'warn', detail: 'Dữ liệu được hiển thị là dữ liệu mẫu được chuẩn bị sẵn.'});
          this.employees = FAKE_EMPLOYEE;
        }
      }).add(() => {

      });
  }

  handleDepartmentSelected(event: any) {
    console.log(event);
  }

  handlePositionSelected(event: any) {
    console.log(event);
  }

  onGlobalFilter(table: any, event: any) {
    table.filterGlobal(
      (event.target as HTMLInputElement).value,
      'contains'
    );
  }

  openAddEmployeeDialog() {
    this.employeeDialogVisible = true;
  }

  deleteSelectedEmployees() {
    this.deleteEmployeesDialogVisible = true;
  }

  onUploadJson(event: any) {
    this.uploadedFiles = event;
  }

  loadEmployeesPage(evnet: any) {

  }

  pageChange(event: any) {

  }

  editEmployee(employee: any, event: any) {
    this.employee = employee;
  }

  deleteEmployee(employee: any, event: any) {
    event.stopPropagation();
    this.employee = employee;
    this.deleteEmployeeDialogVisible = true;
  }

  clickTableCheckbox(event: any) {
    event.stopPropagation();
  }

  saveEmployee() {
    const newEmployee = this.addForm.value;
    this.employeeService.createEmployee(newEmployee).subscribe({
      next: res => {
        console.log(res);
      },
      error: err => {
        console.log(err);
      }
    }).add(() => {

    });
  }
  
  hideDialog() {
    this.employeeDialogVisible = false;
    this.submited = false;
  }

  confirmDelete() {
    this.employeeService.deleteEmployee(this.employee.id).subscribe({
      next: res => {
        console.log(res);
        this.employees = this.employees.filter(item => item.id != this.employee.id);
        this.message.add({ severity: 'info', detail: res.data });
      },
      error: err => {
        console.log(err);
        this.message.add({ severity: 'error', detail: err.message })
      },
      complete: () => {},
    }).add(() => {
      this.employee = null;
      this.deleteEmployeeDialogVisible = false;
    });
  }

  confirmDeleteSelected() {
    this.selectedEmployees.array.forEach(element => {
      this.employees = this.employees.filter(item => item.id != element.id);
    });
    this.deleteEmployeesDialogVisible = false;
  }

  showImportFileDialog() {
    this.importFileDialogVisible = true;
  }

}
