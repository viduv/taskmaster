import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {EnvValueForm} from "../create-edit-process-dialog.component";


@Component({
  selector: 'app-form-env',
  templateUrl: './form-env.component.html',
  styleUrls: ['./form-env.component.scss'],
})
export class FormEnvComponent {

  @Output() delete: EventEmitter<void> = new EventEmitter<void>();

  @Input() group: FormGroup<EnvValueForm> | undefined;

}
