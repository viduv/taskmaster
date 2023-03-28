import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { GroupProcessDetails } from 'src/app/openapi';

@Component({
  selector: 'app-pres-details',
  templateUrl: './pres-details.component.html',
  styleUrls: ['./pres-details.component.scss']
})
export class PresDetailsComponent implements OnInit {

  @Input() groupProcess: GroupProcessDetails = {};
  @Input() outLogs: string = ""
  @Input() errorLogs: string = ""

  @Output() startProc = new EventEmitter<GroupProcessDetails>;
  @Output() stopProc = new EventEmitter<GroupProcessDetails>;
  @Output() getOutLogs = new EventEmitter<string>;
  @Output() getErrorLogs = new EventEmitter<string>;

  indexlogs : number = 0;

  displayedColumns: string[] = ['pid', 'etat'];
    constructor() { }

  ngOnInit(): void {
    // this.getOutlog()
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

  getOutlog(){
    console.log("getOutLog")
    this.getOutLogs.emit(this.groupProcess?.groupProcess?.name)
  }
  getErrorlog(){
    console.log("getErrorLog")
    this.getErrorLogs.emit(this.groupProcess?.groupProcess?.name)
  }
  tabChanged = (tabChangeEvent: MatTabChangeEvent): void => {
    this.indexlogs = tabChangeEvent.index;
    this.indexlogs === 0 ? this.getOutlog() : this.getErrorlog()
}
}
