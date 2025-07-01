import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { Session } from '../../interfaces/session.interface';
import { Teacher } from 'src/app/interfaces/teacher.interface';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let service: SessionService;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
      ],
      declarations: [DetailComponent],
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    }).compileComponents();
    service = TestBed.inject(SessionService);
    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call window.history.back when back() is invoked', () => {
    const spy = jest.spyOn(window.history, 'back');
    component.back();
    expect(spy).toHaveBeenCalled();
  });

  it('should call participate and then fetchSession', () => {
    const participateSpy = jest
      .spyOn(component['sessionApiService'], 'participate')
      .mockReturnValue({ subscribe: (cb: any) => cb({}) } as any);

    const fetchSessionSpy = jest.spyOn(component as any, 'fetchSession');

    component.participate();

    expect(participateSpy).toHaveBeenCalledWith(
      component.sessionId,
      component.userId
    );
    expect(fetchSessionSpy).toHaveBeenCalled();
  });

  it('should call unParticipate and then fetchSession', () => {
    const unParticipateSpy = jest
      .spyOn(component['sessionApiService'], 'unParticipate')
      .mockReturnValue({ subscribe: (cb: any) => cb({}) } as any);

    const fetchSessionSpy = jest.spyOn(component as any, 'fetchSession');

    component.unParticipate();

    expect(unParticipateSpy).toHaveBeenCalledWith(
      component.sessionId,
      component.userId
    );
    expect(fetchSessionSpy).toHaveBeenCalled();
  });

  it('should fetch session, set isParticipate, and fetch teacher detail', () => {
    const mockSession = {
      name: 'Test session',
      date: '2025-07-01',
      teacher_id: 42,
      description: 'desc',
      users: [1],
    } as unknown as Session;

    const mockTeacher: Teacher = {
      id: 42,
      firstName: 'Alice',
      lastName: 'Doe',
      createdAt: new Date('2024-01-01T00:00:00Z'),
      updatedAt: new Date('2024-01-02T00:00:00Z'),
    };

    jest.spyOn(component['sessionApiService'], 'detail').mockReturnValue({
      subscribe: (cb: (session: Session) => void) => cb(mockSession),
    } as any);

    jest.spyOn(component['teacherService'], 'detail').mockReturnValue({
      subscribe: (cb: (teacher: Teacher) => void) => cb(mockTeacher),
    } as any);

    (component as any).fetchSession();

    expect(component.session).toEqual(mockSession);
    expect(component.teacher).toEqual(mockTeacher);
    expect(component.isParticipate).toBe(true);
  });
});
