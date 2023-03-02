import { Injectable, resolveForwardRef } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ExitSignalType, Process, ProcessEtat, RestartType, TaskmasterService } from './openapi';

@Injectable({
  providedIn: 'root'
})
export class SupervisorService {

  constructor( private taskmasterService : TaskmasterService) { }

  listProcess(): Observable<Array<Process>> {
    this.taskmasterService.listProcess()
    return of(new Array<Process>(
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
