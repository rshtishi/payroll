import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartmentEditComponent } from './department-edit.component';

describe('DepartmentEditComponent', () => {
  let component: DepartmentEditComponent;
  let fixture: ComponentFixture<DepartmentEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepartmentEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartmentEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
