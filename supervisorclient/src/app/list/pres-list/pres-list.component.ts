import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { GroupProcess, Process } from 'src/app/openapi';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import { CreateEditProcessDialogComponent } from '../../create-edit-process-dialog/create-edit-process-dialog.component';


@Component({
  selector: 'app-pres-list',
  templateUrl: './pres-list.component.html',
  styleUrls: ['./pres-list.component.scss']
})
export class PresListComponent implements OnInit {
  @Input() processes : Array<GroupProcess> = [];
  @Output() editProcesses = new EventEmitter<GroupProcess>();
  @Output() deleteProcesses = new EventEmitter<GroupProcess>();
  constructor(
    public dialog: MatDialog
  ) { }
  
  ngOnInit(): void {
      console.log(this.processes)
  }

  addProcess() {
    console.log("ADDING ")
    this.dialog.open(CreateEditProcessDialogComponent, {
      data : {
        process: undefined,
        edit : true,
      },
      enterAnimationDuration: '250ms',
      exitAnimationDuration: '250ms',
    });
  }
  editProcess(process: GroupProcess) {
    console.log("EDITING")
    this.dialog.open(CreateEditProcessDialogComponent, {
      data : {
        process,
        edit: false,
      },
      enterAnimationDuration: '250ms',
      exitAnimationDuration: '250ms',
    });
    // this.editProcesses.emit(process);
  }

  deleteProcess(process: GroupProcess) {
    this.deleteProcesses.emit(process);
  }
}
