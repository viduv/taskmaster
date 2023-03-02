import { Component, Input, OnInit } from '@angular/core';
import { Process } from 'src/app/openapi';

@Component({
  selector: 'app-pres-list',
  templateUrl: './pres-list.component.html',
  styleUrls: ['./pres-list.component.scss']
})
export class PresListComponent implements OnInit {
  @Input() processes : Array<Process> = [];

  constructor() { }
  
  ngOnInit(): void {
      
  }

  editProcess(process: Process) {
    console.log(process);
  }
  deleteProcess(process: Process) {
    console.log(process);
  }
}
