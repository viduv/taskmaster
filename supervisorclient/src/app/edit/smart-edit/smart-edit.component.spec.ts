import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmartEditComponent } from './smart-edit.component';

describe('SmartEditComponent', () => {
  let component: SmartEditComponent;
  let fixture: ComponentFixture<SmartEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SmartEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SmartEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
