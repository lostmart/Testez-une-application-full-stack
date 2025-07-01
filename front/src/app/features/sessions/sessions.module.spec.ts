import { expect, it, describe, beforeEach } from '@jest/globals';

import { TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SessionsModule } from './sessions.module';
import { ListComponent } from './components/list/list.component';
import { FormComponent } from './components/form/form.component';
import { DetailComponent } from './components/detail/detail.component';


// stubs
import { SessionService } from 'src/app/services/session.service';



// minimal stub that satisfies both components
const mockSessionService = {
  all: () => of([]), // ListComponent.sessions$
  sessionInformation: {
    // DetailComponent needs .admin & .id
    admin: true,
    id: 42,
  },
};

describe('SessionsModule', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        SessionsModule,
        HttpClientTestingModule, // for any HttpClient deps
        RouterTestingModule.withRoutes([]), // for ActivatedRoute, Router
      ],
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    }).compileComponents();
  });

  it('should create the module', () => {
    expect(TestBed.inject(SessionsModule)).toBeTruthy();
  });

  it('should instantiate ListComponent without error', () => {
    const fixture = TestBed.createComponent(ListComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should instantiate FormComponent without error', () => {
    const fixture = TestBed.createComponent(FormComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should instantiate DetailComponent without error', () => {
    const fixture = TestBed.createComponent(DetailComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should provide FormBuilder from ReactiveFormsModule', () => {
    expect(TestBed.inject(FormBuilder)).toBeDefined();
  });
});
