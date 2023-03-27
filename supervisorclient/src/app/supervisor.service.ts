import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {
  ExitSignalType,
  GroupProcess,
  GroupProcessDetails,
  ProcessEtat,
  RestartType,
  TaskmasterService
} from './openapi';

@Injectable({
  providedIn: 'root'
})
export class SupervisorService {

  constructor(private taskmasterService: TaskmasterService) {
  }

  groupProcess(processName: string): Observable<GroupProcessDetails> {
    return this.taskmasterService.groupProcess(processName);
  }

  listProcess(): Observable<Array<GroupProcess>> {
    return this.taskmasterService.listGroupProcess();
  }
}
