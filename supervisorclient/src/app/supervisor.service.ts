import {Injectable} from '@angular/core';
import {Observable, ReplaySubject} from 'rxjs';
import {GroupProcess, GroupProcessDetails, TaskmasterService} from './openapi';

@Injectable({
  providedIn: 'root'
})
export class SupervisorService {

  groupProcessSubject : ReplaySubject<GroupProcessDetails> = new ReplaySubject<GroupProcessDetails>;
  listProcessSubject : ReplaySubject<Array<GroupProcess>> = new ReplaySubject<Array<GroupProcess>>;
  outLogsSubject : ReplaySubject<string> = new ReplaySubject<string>;
  errorLogsSubject :ReplaySubject<string> = new ReplaySubject<string>;


  constructor(private taskmasterService: TaskmasterService) {
  }

  getGroupProcessSubject(): Observable<GroupProcessDetails> {
    return this.groupProcessSubject.asObservable();
  }

  getListProcessSubject(): Observable<Array<GroupProcess>> {
    return this.listProcessSubject.asObservable();
  }

  getOutlogsSubject(): Observable<string> {
    return this.outLogsSubject.asObservable();
  }

  getErrorLogsSubject(): Observable<string> {
    return this.errorLogsSubject.asObservable();
  }

  groupProcess(processName: string): void {
    this.taskmasterService.groupProcess(processName).subscribe(value => this.groupProcessSubject.next(value))
  }

  listProcess(): void {
    this.taskmasterService.listGroupProcess().subscribe(value => {
      this.listProcessSubject.next(value)
    });
  }

  errorLogs(processName : string): void {
    this.taskmasterService.processLogError(processName).subscribe(value => this.errorLogsSubject.next(value))
  }

  outLogs( processName : string): void {
    this.taskmasterService.processLog(processName).subscribe(value => {
      console.log(value)
      this.outLogsSubject.next(value)})
  }

  startProcess(name: string | undefined): void {
    if(name !== undefined){
    this.taskmasterService.start(name).subscribe(() => this.groupProcess(name))
    }
  }
  stopProcess(name: string | undefined): void {
    if(name !== undefined){
      this.taskmasterService.stop(name).subscribe(() => this.groupProcess(name))
    }
  }
  deleteProcess(name: string | undefined): void {
    if(name !== undefined){
      this.taskmasterService.deleteProcess(name).subscribe(() => this.listProcess())
    }
  }
}
