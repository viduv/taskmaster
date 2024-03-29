import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {GroupProcessDetails} from 'src/app/openapi';
import {SupervisorService} from 'src/app/supervisor.service';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-smart-details',
  template: `
    <app-pres-details
      style="height: calc(100% - 64px); display: block;"
      [groupProcess]="(supervisorService.getGroupProcessSubject() | async) ?? {}"
      [outLogs]="(supervisorService.getOutlogsSubject() | async) ?? ''"
      [errorLogs]="(supervisorService.getErrorLogsSubject() | async) ?? ''"
      (startProc)="startProc($event)"
      (stopProc)="stopProc($event)"
      (getErrorLogs)="getErrorlog($event)"
      (getOutLogs)="getOutlog($event)"
    ></app-pres-details>`,
})
export class SmartDetailsComponent implements OnInit, OnDestroy {
  routerSubscription: Subscription | undefined = undefined;

  constructor(
    public supervisorService: SupervisorService,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.routerSubscription = this.activatedRoute.params.subscribe(params => {
      this.supervisorService.groupProcess(params["id"]);
    })
  }

  startProc(groupProcess: GroupProcessDetails) {
    this.supervisorService.startProcess(groupProcess?.groupProcess?.name)
  }

  stopProc(groupProcess: GroupProcessDetails) {
    this.supervisorService.stopProcess(groupProcess?.groupProcess?.name)
  }

  getOutlog(processName: string) {
    this.supervisorService.outLogs(processName)
  }

  getErrorlog(processName: string) {
    this.supervisorService.errorLogs(processName)
  }

  ngOnDestroy(): void {
    this.routerSubscription?.unsubscribe()
  }
}
