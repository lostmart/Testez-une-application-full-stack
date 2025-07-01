import { TestBed } from '@angular/core/testing';
import {
  HTTP_INTERCEPTORS,
  HttpClient,
  HttpHandler,
  HttpRequest,
  HttpEvent,
  HttpResponse,
} from '@angular/common/http';
import { JwtInterceptor } from './jwt.interceptor';
import { SessionService } from '../services/session.service';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { expect, it, describe, beforeEach } from '@jest/globals';

describe('JwtInterceptor', () => {
  let httpClient: HttpClient;
  let httpMock: HttpTestingController;
  let sessionService: SessionService;

  const mockSessionService = {
    isLogged: true,
    sessionInformation: {
      token: 'fake-jwt-token',
    },
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        {
          provide: HTTP_INTERCEPTORS,
          useClass: JwtInterceptor,
          multi: true,
        },
      ],
    });

    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
    sessionService = TestBed.inject(SessionService);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should add Authorization header if user is logged in', () => {
    httpClient.get('/test').subscribe();

    const req = httpMock.expectOne('/test');
    expect(req.request.headers.has('Authorization')).toBe(true);
    expect(req.request.headers.get('Authorization')).toBe(
      'Bearer fake-jwt-token'
    );
    req.flush({});
  });

  it('should not add Authorization header if user is not logged in', () => {
    mockSessionService.isLogged = false;

    httpClient.get('/test').subscribe();

    const req = httpMock.expectOne('/test');
    expect(req.request.headers.has('Authorization')).toBe(false);
    req.flush({});
  });
});
