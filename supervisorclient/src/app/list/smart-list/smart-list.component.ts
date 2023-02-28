import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Process } from 'src/app/openapi';
import {TaskmasterService} from 'src/app/openapi/api/taskmaster.service';
@Component({
  selector: 'app-smart-list',
  template: `<app-pres-list
  [processes]="(listProcess | async) ?? []"
  ></app-pres-list>`,
})
export class SmartListComponent implements OnInit {

  listProcess: Observable<Array<Process>> = new Observable<Array<Process>>();
  constructor(
    private taskmasterService: TaskmasterService) 
    {}
  ngOnInit(): void {
    this.listProcess = this.taskmasterService.listProcess();
  }

}
