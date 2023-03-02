import { Component, Input, OnInit } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { GroupProcessDetails } from 'src/app/openapi';

@Component({
  selector: 'app-pres-details',
  templateUrl: './pres-details.component.html',
  styleUrls: ['./pres-details.component.scss']
})
export class PresDetailsComponent implements OnInit {
  
  @Input() groupProcess: GroupProcessDetails = {}
  displayedColumns: string[] = ['pid', 'etat'];
    constructor() { }

  ngOnInit(): void {
  }

  getRadioColor(etat: string): ThemePalette {
    return etat === 'RUN' ? 'primary' : etat === 'PARTIAL' ? 'accent' : etat === 'STOP' ? 'warn' : 'primary'
  }
}
