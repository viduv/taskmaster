import { Component, Input, OnInit } from '@angular/core';
import { GroupProcess, Process } from 'src/app/openapi';

@Component({
  selector: 'app-pres-list',
  templateUrl: './pres-list.component.html',
  styleUrls: ['./pres-list.component.scss']
})
export class PresListComponent implements OnInit {
  @Input() processes : Array<GroupProcess> = [];

  constructor() { }
  
  ngOnInit(): void {
      
  }

  editProcess(process: GroupProcess) {
    console.log(process);
  }
  deleteProcess(process: GroupProcess) {
    console.log(process);
  }
}
