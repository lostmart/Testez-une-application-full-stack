import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';

import { FormBuilder } from '@angular/forms';

import { Session } from '../../interfaces/session.interface';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let fb: FormBuilder;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        NoopAnimationsModule,
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        SessionApiService,
      ],
      declarations: [FormComponent],
    }).compileComponents();

    fb = TestBed.inject(FormBuilder); // ✅ This line is required
    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize an empty form when no session is provided', () => {
    component.initForm();
    expect(component.sessionForm?.value).toEqual({
      name: '',
      date: '',
      teacher_id: '',
      description: '',
    });
  });

  it('should initialize the form with provided session data', () => {
    const mockSession = {
      name: 'Yoga Class',
      date: new Date('2025-07-01'),
      teacher_id: 4564,
      description: 'Morning yoga session',
      users: [],
    };

    component.initForm(mockSession);
    expect(component.sessionForm?.value).toEqual({
      name: 'Yoga Class',
      date: '2025-07-01',
      teacher_id: 4564,
      description: 'Morning yoga session',
    });
  });

  it('should initialize the form with provided session data', () => {
    const mockSession = {
      name: 'Yoga Class',
      date: new Date('2025-07-01'),
      teacher_id: 4564,
      description: 'Morning yoga session',
      users: [],
    };

    component.initForm(mockSession);
    expect(component.sessionForm?.value).toEqual({
      name: 'Yoga Class',
      date: '2025-07-01',
      teacher_id: 4564,
      description: 'Morning yoga session',
    });
  });

  it('should call update and exitPage when updating', () => {
    const mockSession = {
      name: 'Session 1',
      date: '2025-07-01',
      teacher_id: 123,
      description: 'Description',
      users: [], // this won't be used
    };

    component.onUpdate = true;
    component.id = 'abc123';
    component.sessionForm = fb.group({
      name: [mockSession.name],
      date: [mockSession.date],
      teacher_id: [mockSession.teacher_id],
      description: [mockSession.description],
    });

    const updateSpy = jest
      .spyOn(component['sessionApiService'], 'update')
      .mockReturnValue({
        subscribe: (cb: (val: Session) => void) => cb(mockSession),
      } as any);

    const exitSpy = jest.spyOn<any, any>(component as any, 'exitPage');

    component.submit();

    expect(updateSpy).toHaveBeenCalledWith(
      'abc123',
      component.sessionForm?.value
    );
    expect(exitSpy).toHaveBeenCalledWith('Session updated !');
  });
});
