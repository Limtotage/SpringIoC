import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Visitor } from './visitor';

describe('Visitor', () => {
  let component: Visitor;
  let fixture: ComponentFixture<Visitor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Visitor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Visitor);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
