import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupProcessDetails } from 'src/app/openapi';
import { SupervisorService } from 'src/app/supervisor.service';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-smart-details',
  template: `<app-pres-details
  style="height: calc(100% - 64px); display: block;"
  [groupProcess]="(groupProcess | async) ?? {}"
  (startProc)="startProc($event)"
  (stopProc)="stopProc($event)"
  ></app-pres-details>`,
})
export class SmartDetailsComponent implements OnInit {

  groupProcess: Observable<GroupProcessDetails> = new Observable<GroupProcessDetails>();

  constructor(
    private supervisorService: SupervisorService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.groupProcess = this.supervisorService.groupProcess(params["id"]);
    })
  }

  startProc(groupProcess : GroupProcessDetails){

  }

  stopProc(groupProcess: GroupProcessDetails){

  }

}
