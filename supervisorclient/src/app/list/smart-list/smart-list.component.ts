import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupProcess, Process } from 'src/app/openapi';
import { SupervisorService } from 'src/app/supervisor.service';
@Component({
  selector: 'app-smart-list',
  template: `<app-pres-list
  [processes]="(supervisorService.getListProcessSubject() | async) ?? []"
  (deleteProcesses)="deleteProcess($event)"
  (editProcesses)="editProcess($event)"
  ></app-pres-list>`,
  styles: ['app-pres-list { height: calc(100% - 64px); display: block;}']

})
export class SmartListComponent implements OnInit {

  listProcess: Observable<Array<GroupProcess>> = new Observable<Array<GroupProcess>>();
  constructor(
    public supervisorService: SupervisorService) 
    {}

  ngOnInit(): void {
    this.supervisorService.listProcess();
  }

  editProcess(process: GroupProcess) {
    console.log(process);
  }
  deleteProcess(process: GroupProcess) {
    this.supervisorService.deleteProcess(process?.name);
  }
}
