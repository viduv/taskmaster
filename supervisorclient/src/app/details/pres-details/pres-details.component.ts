import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { GroupProcessDetails } from 'src/app/openapi';

@Component({
  selector: 'app-pres-details',
  templateUrl: './pres-details.component.html',
  styleUrls: ['./pres-details.component.scss']
})
export class PresDetailsComponent implements OnChanges{

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

  ngOnChanges(changes: SimpleChanges): void {
    if("groupProcess" in changes && this.groupProcess?.groupProcess?.name){
      this.getOutlog()
      console.log(this.groupProcess)
    }
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
    this.getOutLogs.emit(this.groupProcess?.groupProcess?.name)
  }
  getErrorlog(){
    this.getErrorLogs.emit(this.groupProcess?.groupProcess?.name)
  }
  tabChanged = (tabChangeEvent: MatTabChangeEvent): void => {
    this.indexlogs = tabChangeEvent.index;
    this.indexlogs === 0 ? this.getOutlog() : this.getErrorlog()
}
}
