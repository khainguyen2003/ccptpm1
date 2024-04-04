import { Component, OnInit } from '@angular/core';
import { Department } from 'src/app/models/Department';
import { Position } from 'src/app/models/Position';
import { FAKE_DEPARTMENT, FAKE_EMPLOYEE, FAKE_POSITION } from './fake-data';
import { FormGroup } from '@angular/forms';
import { MenuItem } from 'primeng/api';
import { Employee } from 'src/app/models/employee';

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
  employees: Employee[] = FAKE_EMPLOYEE;
  selectedEmployees: any = [];
  employee: any;
  rowsPerPageOptions = [10, 25, 50];
  errorMessage = '';
  headerEmployeeDialog: any;
  addForm: FormGroup;
  submited: boolean = false;
  uploadedFiles: any = [];

  constructor() {}

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

  }

  hideDialog() {
    this.employeeDialogVisible = false;
    this.submited = false;
  }

  confirmDelete() {
    this.employees = this.employees.filter(item => item.id != this.employee.id);
    this.employee = null;
    this.deleteEmployeeDialogVisible = false;
  }

  confirmDeleteSelected() {

  }

  showImportFileDialog() {
    this.importFileDialogVisible = true;
  }

}
