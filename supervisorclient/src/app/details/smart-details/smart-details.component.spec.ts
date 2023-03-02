import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmartDetailsComponent } from './smart-details.component';

describe('SmartDetailsComponent', () => {
  let component: SmartDetailsComponent;
  let fixture: ComponentFixture<SmartDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SmartDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SmartDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
