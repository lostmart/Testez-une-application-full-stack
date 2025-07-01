import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { SessionService } from '../services/session.service';

import { expect, it, describe, beforeEach } from '@jest/globals';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let router: Router;
  let sessionService: SessionService;

  const routerMock = {
    navigate: jest.fn(),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AuthGuard,
        { provide: Router, useValue: routerMock },
        {
          provide: SessionService,
          useValue: { isLogged: false },
        },
      ],
    });

    guard = TestBed.inject(AuthGuard);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
  });

  it('should return false and redirect to login if not logged in', () => {
    sessionService.isLogged = false;

    const result = guard.canActivate();

    expect(result).toBe(false);
    expect(router.navigate).toHaveBeenCalledWith(['login']);
  });

  it('should return true if logged in', () => {
    sessionService.isLogged = true;

    // Clear previous calls to avoid false positives
    (router.navigate as jest.Mock).mockClear();

    const result = guard.canActivate();

    expect(result).toBe(true);
    expect(router.navigate).not.toHaveBeenCalled();
  });
});
