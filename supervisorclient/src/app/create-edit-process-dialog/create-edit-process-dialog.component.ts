import { Component, Inject } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EnvValue, ExitSignalType, GroupProcess, ProcessEtat } from '../openapi';
import { RestartType } from '../openapi/model/restartType';

interface GroupProcessForm {
  name: FormControl<string>,
  nbInstance: FormControl<number>,
  startAtLaunch: FormControl<boolean>,
  restartType: FormControl<RestartType>,
  expectedExitCode: FormControl<number>,
  startupTime: FormControl<number>,
  restartRetryCount: FormControl<number>,
  exitSignal: FormControl<ExitSignalType>,
  gracefulStopTime: FormControl<number>,
  environement: FormArray<FormControl<EnvValue>>,
  umask: FormControl<string>,
  etat: FormControl<ProcessEtat>,
}


@Component({
  selector: 'app-create-edit-process-dialog',
  templateUrl: './create-edit-process-dialog.component.html',
  styleUrls: ['./create-edit-process-dialog.component.scss']
})
export class CreateEditProcessDialogComponent {

  group: FormGroup<GroupProcessForm> = new FormGroup<GroupProcessForm>(
    {
      name: new FormControl<string>("", { nonNullable: true, validators: [Validators.required]}),
      nbInstance: new FormControl<number>(1,  { nonNullable: true, validators: [Validators.required]}),
      startAtLaunch: new FormControl<boolean>(true,  { nonNullable: true, validators: [Validators.required]}),
      restartType: new FormControl<RestartType>(RestartType.Never, { nonNullable: true, validators: [Validators.required]}),
      expectedExitCode: new FormControl<number>(0, { nonNullable: true, validators: [Validators.required]}),
      startupTime: new FormControl<number>(5,  { nonNullable: true, validators: [Validators.required]}),
      restartRetryCount: new FormControl<number>(1,  { nonNullable: true, validators: [Validators.required, Validators.min(1), Validators.max(2000)]}),
      exitSignal: new FormControl<ExitSignalType>(ExitSignalType.Sigint,  { nonNullable: true, validators: [Validators.required]}),
      gracefulStopTime: new FormControl<number>(5,  { nonNullable: true, validators: [Validators.required]}),
      environement: new FormArray<FormControl<EnvValue>>([new FormControl<EnvValue>({key: "coucou", value : "coucou"}, { nonNullable: true, validators: [Validators.required]})]),
      umask: new FormControl<string>("022",  { nonNullable: true, validators: [Validators.required]}),
      etat: new FormControl<ProcessEtat>(ProcessEtat.Run , { nonNullable: true, validators: [Validators.required]})
    }
  );
  constructor( 
    public dialogRef: MatDialogRef<GroupProcess>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    ) { }
  
    ngOnInit(): void {
      if (this.data.process) {
        this.group.patchValue(this.data.process);
      }

      }
    restartValue(){
      return [RestartType.Always,  RestartType.Never, RestartType.OnFailure,];
    }
}
