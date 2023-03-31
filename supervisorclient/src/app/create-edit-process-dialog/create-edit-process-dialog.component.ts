import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ExitSignalType, GroupProcess, ProcessEtat} from '../openapi';
import {RestartType} from '../openapi/model/restartType';
import {SupervisorService} from "../supervisor.service";

interface GroupProcessForm {
  name: FormControl<string>,
  command: FormControl<string>,
  nbInstance: FormControl<number>,
  startAtLaunch: FormControl<boolean>,
  restartType: FormControl<RestartType>,
  expectedExitCode: FormControl<number>,
  startupTime: FormControl<number>,
  restartRetryCount: FormControl<number>,
  exitSignal: FormControl<ExitSignalType>,
  gracefulStopTime: FormControl<number>,
  environement: FormArray<FormGroup<EnvValueForm>>,
  umask: FormControl<string>,
  etat: FormControl<ProcessEtat>,
  stdout: FormControl<string | undefined>,
  stderr: FormControl<string | undefined>,
  workingdir: FormControl<string | undefined>
}

export interface EnvValueForm {
  key: FormControl<string>,
  value: FormControl<string>
}


@Component({
  selector: 'app-create-edit-process-dialog',
  templateUrl: './create-edit-process-dialog.component.html',
  styleUrls: ['./create-edit-process-dialog.component.scss']
})
export class CreateEditProcessDialogComponent implements OnInit {

  group: FormGroup<GroupProcessForm> = new FormGroup<GroupProcessForm>(
    {
      name: new FormControl<string>("", {nonNullable: true, validators: [Validators.required]}),
      command: new FormControl<string>("", {nonNullable: true, validators: [Validators.required]}),
      nbInstance: new FormControl<number>(1, {nonNullable: true, validators: [Validators.required]}),
      startAtLaunch: new FormControl<boolean>(true, {nonNullable: true, validators: [Validators.required]}),
      restartType: new FormControl<RestartType>(RestartType.Always, {
        nonNullable: true,
        validators: [Validators.required]
      }),
      expectedExitCode: new FormControl<number>(0, {nonNullable: true, validators: [Validators.required]}),
      startupTime: new FormControl<number>(5, {nonNullable: true, validators: [Validators.required]}),
      restartRetryCount: new FormControl<number>(1, {
        nonNullable: true,
        validators: [Validators.required, Validators.min(1), Validators.max(2000)]
      }),
      exitSignal: new FormControl<ExitSignalType>(ExitSignalType.Sigint, {
        nonNullable: true,
        validators: [Validators.required]
      }),
      gracefulStopTime: new FormControl<number>(5, {nonNullable: true, validators: [Validators.required]}),
      environement: new FormArray<FormGroup<EnvValueForm>>([]),
      umask: new FormControl<string>("022", {nonNullable: true, validators: [Validators.required]}),
      etat: new FormControl<ProcessEtat>({value: ProcessEtat.Run, disabled: true}, {
        nonNullable: true,
        validators: [Validators.required]
      }),
      stdout: new FormControl<string | undefined>("", {nonNullable: true}),
      stderr: new FormControl<string | undefined>("", {nonNullable: true}),
      workingdir: new FormControl<string | undefined>("", {nonNullable: true}),
    }
  );

  constructor(
    public dialogRef: MatDialogRef<GroupProcess>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private changeDetectorRef: ChangeDetectorRef,
    private supervisorService: SupervisorService
  ) {
  }

  ngOnInit(): void {
    if (this.data?.process) {
      this.group.patchValue(this.data.process);
    }
    if(this.data?.forbiddenName?.length > 0){
      this.group.controls.name.setValidators([Validators.required, this.notInArrayValidator(this.data.forbiddenName)]);
    }
  }

  get formEnvironement(): FormArray<FormGroup<EnvValueForm>> {
    return this.group.controls.environement as FormArray<FormGroup<EnvValueForm>>;
  }

  restartValue() {
    return [RestartType.Always, RestartType.Never, RestartType.OnFailure,];
  }

  exitSignals() {
    return [ExitSignalType.Sigint, ExitSignalType.Sigterm, ExitSignalType.Sigquit, ExitSignalType.Sigkill];
  }

  getReturnData() {
    if (this.data.edit) {
      this.supervisorService.editProcess(this.data.process.name, this.group.getRawValue());
    } else {
      this.supervisorService.createProcess(this.group.getRawValue());
    }
  }

  addEnvForm() {
    this.formEnvironement.push(new FormGroup<EnvValueForm>({
      key: new FormControl("", {nonNullable: true}),
      value: new FormControl("", {nonNullable: true})
    }));
    this.changeDetectorRef.detectChanges();
  }

  deleteEnvForm(index: number) {
    this.formEnvironement.removeAt(index)
  }

  notInArrayValidator(array: string[]): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const value = control.value;
      if (array.indexOf(value) !== -1) {
        return { notInArray: true };
      }
      return null;
    };
  }
}
