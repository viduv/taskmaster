import { Injectable, resolveForwardRef } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ExitSignalType, GroupProcess, GroupProcessDetails, Process, ProcessEtat, RestartType, TaskmasterService } from './openapi';

@Injectable({
  providedIn: 'root'
})
export class SupervisorService {

  constructor( private taskmasterService : TaskmasterService) { }

  groupProcess(): Observable<GroupProcessDetails> {
    this.taskmasterService.groupProcess("test")
    return of(
      { 
        groupProcess : 
          {
            name: "test", 
            nbInstance: 1, 
            startAtLaunch: true, 
            restartType: RestartType.Always, 
            expectedExitCode: 0, 
            startupTime: 0, 
            restartRetryCount: 0, 
            exitSignal: ExitSignalType.Sigint, 
            gracefulStopTime: 0, 
            environement: [], 
            umask: "0000", 
            etat: ProcessEtat.Run
          },
        processes:
        [{
          pid: 1333,
          etat: ProcessEtat.Run,
        },
        {
          pid: 3223,
          etat: ProcessEtat.Partial,
        },
        {
          pid: 122332,
          etat: ProcessEtat.Stop,
        },
      ]
      })
      }

  listProcess(): Observable<Array<GroupProcess>> {
    this.taskmasterService.listGroupProcess()
    return of(new Array<GroupProcess>(
      {
        name: "test", 
        nbInstance: 1, 
        startAtLaunch: true, 
        restartType: RestartType.Always, 
        expectedExitCode: 0, 
        startupTime: 0, 
        restartRetryCount: 0, 
        exitSignal: ExitSignalType.Sigint, 
        gracefulStopTime: 0, 
        environement: [], 
        umask: "0000", 
        etat: ProcessEtat.Run
      },
      {
      name: "test", 
      nbInstance: 1, 
      startAtLaunch: true, 
      restartType: RestartType.Always, 
      expectedExitCode: 0, 
      startupTime: 0, 
      restartRetryCount: 0, 
      exitSignal: ExitSignalType.Sigint, 
      gracefulStopTime: 0, 
      environement: [], 
      umask: "0000", 
      etat: ProcessEtat.Run
      },
      {
      name: "test", 
      nbInstance: 1, 
      startAtLaunch: true, 
      restartType: RestartType.Always, 
      expectedExitCode: 0, 
      startupTime: 0, 
      restartRetryCount: 0, 
      exitSignal: ExitSignalType.Sigint, 
      gracefulStopTime: 0, 
      environement: [], 
      umask: "0000", 
      etat: ProcessEtat.Run
      },
      {
        name: "test", 
        nbInstance: 1, 
        startAtLaunch: true, 
        restartType: RestartType.Always, 
        expectedExitCode: 0, 
        startupTime: 0, 
        restartRetryCount: 0, 
        exitSignal: ExitSignalType.Sigint, 
        gracefulStopTime: 0, 
        environement: [], 
        umask: "0000", 
        etat: ProcessEtat.Run
        },
      ))
  }
}
