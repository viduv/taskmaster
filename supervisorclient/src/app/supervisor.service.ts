import {Injectable} from '@angular/core';
import {ReplaySubject} from 'rxjs';
import {GroupProcess, GroupProcessDetails, TaskmasterService} from './openapi';

@Injectable({
  providedIn: 'root'
})
export class SupervisorService {

  groupProcessSubject : ReplaySubject<GroupProcessDetails> = new ReplaySubject<GroupProcessDetails>;
  listProcessSubject : ReplaySubject<Array<GroupProcess>> = new ReplaySubject<Array<GroupProcess>>;


  constructor(private taskmasterService: TaskmasterService) {
  }

  getGroupProcessSubject(): ReplaySubject<GroupProcessDetails> {
    return this.groupProcessSubject;
  }

  getListProcessSubject(): ReplaySubject<Array<GroupProcess>> {
    return this.listProcessSubject;
  }

  groupProcess(processName: string): void {
    this.taskmasterService.groupProcess(processName).subscribe(value => this.groupProcessSubject.next(value))
  }

  listProcess(): void {
    this.taskmasterService.listGroupProcess().subscribe(value => {
      this.listProcessSubject.next(value)
    });
  }
}
