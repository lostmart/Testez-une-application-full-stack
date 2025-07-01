import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';

import { expect, it, describe, beforeEach } from '@jest/globals';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });

    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve a session by id', () => {
    const mockSession: Session = {
      name: 'Session A',
      date: '2025-01-01',
      teacher_id: 123,
      description: 'A sample session',
      users: [],
    };

    service.detail('123').subscribe((session) => {
      expect(session).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session/123');
    expect(req.request.method).toBe('GET');
    req.flush(mockSession);
  });

  it('should delete a session by id', () => {
    const sessionId = '456';

    service.delete(sessionId).subscribe((response) => {
      expect(response).toBeNull(); // assuming backend returns no content (204)
    });

    const req = httpMock.expectOne(`api/session/${sessionId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null); // simulate 204 No Content
  });

  it('should create a new session', () => {
    const mockSession: Session = {
      name: 'New Session',
      date: '2025-07-01',
      teacher_id: 101,
      description: 'Test session creation',
      users: [],
    };

    service.create(mockSession).subscribe((response) => {
      expect(response).toEqual(mockSession);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockSession);
    req.flush(mockSession);
  });

  it('should update a session by id', () => {
    const sessionId = '789';
    const mockSession: Session = {
      name: 'Updated Session',
      date: '2025-08-01',
      teacher_id: 222,
      description: 'Updated session description',
      users: [],
    };

    service.update(sessionId, mockSession).subscribe((response) => {
      expect(response).toEqual(mockSession);
    });

    const req = httpMock.expectOne(`api/session/${sessionId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockSession);
    req.flush(mockSession);
  });

  it('should call participate API for a user and session', () => {
    const sessionId = '321';
    const userId = '99';

    service.participate(sessionId, userId).subscribe((response) => {
      expect(response).toBeUndefined(); // since it's void
    });

    const req = httpMock.expectOne(
      `api/session/${sessionId}/participate/${userId}`
    );
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toBeNull();
    req.flush(null);
  });

  it('should call unParticipate API for a user and session', () => {
    const sessionId = '321';
    const userId = '99';

    service.unParticipate(sessionId, userId).subscribe((response) => {
      expect(response).toBeUndefined(); // since it's void
    });

    const req = httpMock.expectOne(
      `api/session/${sessionId}/participate/${userId}`
    );
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});
