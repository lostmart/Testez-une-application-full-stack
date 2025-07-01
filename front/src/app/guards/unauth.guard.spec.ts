import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { UnauthGuard } from './unauth.guard';
import { SessionService } from '../services/session.service';

import { expect, it, describe, beforeEach } from '@jest/globals';

describe('UnauthGuard', () => {
  let guard: UnauthGuard;
  let router: Router;
  let sessionService: SessionService;

  const routerMock = {
    navigate: jest.fn(),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        UnauthGuard,
        { provide: Router, useValue: routerMock },
        {
          provide: SessionService,
          useValue: { isLogged: false },
        },
      ],
    });

    guard = TestBed.inject(UnauthGuard);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
  });

  afterEach(() => {
    jest.clearAllMocks(); // ✅ Important to avoid leftover call history
  });

  it('should return true if user is not logged in', () => {
    sessionService.isLogged = false;

    const result = guard.canActivate();

    expect(result).toBe(true);
    expect(router.navigate).not.toHaveBeenCalled();
  });

  it('should return false and redirect to rentals if user is logged in', () => {
    sessionService.isLogged = true;

    const result = guard.canActivate();

    expect(result).toBe(false);
    expect(router.navigate).toHaveBeenCalledWith(['rentals']);
  });
});
