import { Component, Inject } from '@angular/core';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GroupProcess } from '../openapi';

@Component({
  selector: 'app-create-edit-process-dialog',
  templateUrl: './create-edit-process-dialog.component.html',
  styleUrls: ['./create-edit-process-dialog.component.scss']
})
export class CreateEditProcessDialogComponent {

  group: UntypedFormGroup = new UntypedFormGroup({});
  constructor( 
    public dialogRef: MatDialogRef<GroupProcess>,
    @Inject(MAT_DIALOG_DATA) public data: any,) { }
  
    ngOnInit(): void {
      console.log(this.data)
        this.group = new UntypedFormGroup({
          name: new FormControl(this.data?.name, [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),
          nbInstance: new FormControl(this.data?.nbInstance),
          startAtLaunch: new FormControl(this.data?.startAtLaunch),
          restartType: new FormControl(this.data?.restartType),
          expectedExitCode: new FormControl(this.data?.expectedExitCode),
          startupTime: new FormControl(this.data?.startupTime),
          restartRetryCount: new FormControl(this.data?.restartRetryCount),
          exitSignal: new FormControl(this.data?.exitSignal),
          gracefulStopTime: new FormControl(this.data?.gracefulStopTime),
          environement: new FormControl(this.data?.environement),
          umask: new FormControl(this.data?.umask),
          etat: new FormControl(this.data?.etat),
        })
      }
}
