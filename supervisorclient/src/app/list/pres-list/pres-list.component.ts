import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupProcess} from 'src/app/openapi';
import {MatDialog} from '@angular/material/dialog';
import {CreateEditProcessDialogComponent} from '../../create-edit-process-dialog/create-edit-process-dialog.component';


@Component({
  selector: 'app-pres-list',
  templateUrl: './pres-list.component.html',
  styleUrls: ['./pres-list.component.scss']
})
export class PresListComponent implements OnInit {
  @Input() processes: Array<GroupProcess> = [];
  @Output() editProcesses = new EventEmitter<GroupProcess>();
  @Output() deleteProcesses = new EventEmitter<GroupProcess>();

  constructor(
    public dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    console.log(this.processes)
  }

  addProcess() {
    console.log("ADDING ")
    this.dialog.open(CreateEditProcessDialogComponent, {
      width: '70%',
      height: '80%',
      data: {
        process: undefined,
        edit: false,
      },
      enterAnimationDuration: '250ms',
      exitAnimationDuration: '250ms',
    });
  }

  editProcess(process: GroupProcess) {
    console.log("EDITING")
    this.dialog.open(CreateEditProcessDialogComponent, {
      width: '70%',
      height: '80%',
      data: {
        process,
        edit: true,
      },
      enterAnimationDuration: '250ms',
      exitAnimationDuration: '250ms',
    });
  }

  deleteProcess(process: GroupProcess) {
    this.deleteProcesses.emit(process);
  }
}
