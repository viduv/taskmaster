import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PresEditComponent } from './pres-edit.component';

describe('PresEditComponent', () => {
  let component: PresEditComponent;
  let fixture: ComponentFixture<PresEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PresEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PresEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
