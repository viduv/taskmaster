import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SmartListComponent } from './list';
import { SmartEditComponent } from './edit';

const routes: Routes = [
  { path: 'list', component: SmartListComponent},
  { path: 'edit', component: SmartEditComponent},
  { path: '', redirectTo: 'list', pathMatch: 'full' },
  { path: '**', redirectTo: 'list', pathMatch: 'full' }

];
@NgModule({ 
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
