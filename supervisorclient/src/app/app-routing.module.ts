import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SmartListComponent } from './list';
import { SmartDetailsComponent } from './details';

const routes: Routes = [
  { path: 'list', component: SmartListComponent},
  { path: 'details/:id', component: SmartDetailsComponent},
  { path: '', redirectTo: 'list', pathMatch: 'full' },
  { path: '**', redirectTo: 'list', pathMatch: 'full' }

];
@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
