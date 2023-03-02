// imports
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { PresListComponent, SmartListComponent } from './list';
import { PresEditComponent, SmartEditComponent  } from './edit';
import { ApiModule } from './openapi';
import {MatCardModule} from '@angular/material/card';
import { HttpClientModule } from '@angular/common/http';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { SmartDetailsComponent, PresDetailsComponent } from './details';

// @NgModule decorator with its metadata
@NgModule({
  declarations: [AppComponent, SmartListComponent, PresListComponent, SmartEditComponent, PresEditComponent, SmartDetailsComponent, PresDetailsComponent],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, RouterModule, ApiModule, MatCardModule, MatButtonModule, MatToolbarModule, MatIconModule, MatProgressSpinnerModule ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}