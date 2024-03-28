import { Component } from '@angular/core';
import { Department } from 'src/app/models/Department';
import { Jobtitle } from 'src/app/models/Jobtitle';
import { FAKE_DEPARTMENT, FAKE_JOBTITLE } from './fake-data';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.scss'
})
export class EmployeeComponent {

  optDepartment: Department[] = FAKE_DEPARTMENT;
  optJobtitle: Jobtitle[] = FAKE_JOBTITLE;

  handleDepartmentSelected(event: any) {
    console.log(event);
  }

  handleJobtitleSelected(event: any) {
    console.log(event);
  }

}
