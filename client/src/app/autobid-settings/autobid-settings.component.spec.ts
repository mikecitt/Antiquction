import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutobidSettingsComponent } from './autobid-settings.component';

describe('AutobidSettingsComponent', () => {
  let component: AutobidSettingsComponent;
  let fixture: ComponentFixture<AutobidSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutobidSettingsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutobidSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
