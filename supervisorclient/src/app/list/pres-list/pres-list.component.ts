import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { GroupProcess, Process } from 'src/app/openapi';

@Component({
  selector: 'app-pres-list',
  templateUrl: './pres-list.component.html',
  styleUrls: ['./pres-list.component.scss']
})
export class PresListComponent implements OnInit {
  @Input() processes : Array<GroupProcess> = [];
  @Output() editProcesses = new EventEmitter<GroupProcess>();
  @Output() deleteProcesses = new EventEmitter<GroupProcess>();
  constructor() { }
  
  ngOnInit(): void {
      console.log(this.processes)
  }

  editProcess(process: GroupProcess) {
    console.log(process);
    this.editProcesses.emit(process);
  }
  deleteProcess(process: GroupProcess) {
    console.log(process);
    this.deleteProcesses.emit(process);
  }
}
