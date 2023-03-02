import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Process } from 'src/app/openapi';
import { SupervisorService } from 'src/app/supervisor.service';
@Component({
  selector: 'app-smart-list',
  template: `<app-pres-list
  [processes]="(listProcess | async) ?? []"
  ></app-pres-list>`,
})
export class SmartListComponent implements OnInit {

  listProcess: Observable<Array<Process>> = new Observable<Array<Process>>();
  constructor(
    private supervisorService: SupervisorService) 
    {}
  ngOnInit(): void {
    this.listProcess = this.supervisorService.listProcess();
  }
}
