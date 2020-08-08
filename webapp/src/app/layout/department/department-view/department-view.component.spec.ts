import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartmentViewComponent } from './department-view.component';

describe('DepartmentViewComponent', () => {
  let component: DepartmentViewComponent;
  let fixture: ComponentFixture<DepartmentViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepartmentViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartmentViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
