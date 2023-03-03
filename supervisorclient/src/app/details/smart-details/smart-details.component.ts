import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupProcessDetails } from 'src/app/openapi';
import { SupervisorService } from 'src/app/supervisor.service';

@Component({
  selector: 'app-smart-details',
  template: `<app-pres-details
  style="height: calc(100% - 64px); display: block;"
  [groupProcess]="(groupProcess | async) ?? {}"
  ></app-pres-details>`,
})
export class SmartDetailsComponent implements OnInit {

  groupProcess: Observable<GroupProcessDetails> = new Observable<GroupProcessDetails>();
  constructor(
    private supervisorService: SupervisorService
  ) { }

  ngOnInit(): void {
    this.groupProcess = this.supervisorService.groupProcess();
  }

}
