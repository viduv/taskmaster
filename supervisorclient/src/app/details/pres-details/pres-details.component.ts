import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { GroupProcessDetails } from 'src/app/openapi';

@Component({
  selector: 'app-pres-details',
  templateUrl: './pres-details.component.html',
  styleUrls: ['./pres-details.component.scss']
})
export class PresDetailsComponent implements OnInit {
  
  @Input() groupProcess: GroupProcessDetails = {}
  @Output() startProc = new EventEmitter<GroupProcessDetails>;
  @Output() stopProc = new EventEmitter<GroupProcessDetails>;
  displayedColumns: string[] = ['pid', 'etat'];
    constructor() { }

  ngOnInit(): void {
  }

  startProcess(){
    this.startProc.emit(this.groupProcess);
  }
  stopProcess(){
    this.stopProc.emit(this.groupProcess);
  }
  getRadioColor(etat: string): ThemePalette {
    return etat === 'RUN' ? 'primary' : etat === 'PARTIAL' ? 'accent' : etat === 'STOP' ? 'warn' : 'primary'
  }
  getTooltip(etat: string): string {
    return etat === 'RUN' ? 'running' : etat === 'PARTIAL' ? 'partial' : etat === 'STOP' ? 'stop' : ''
  }
}
