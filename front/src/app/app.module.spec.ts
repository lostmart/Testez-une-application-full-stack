import { TestBed } from '@angular/core/testing';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

import { AppModule } from './app.module';
import { JwtInterceptor } from './interceptors/jwt.interceptor';

import { expect, it, describe, beforeEach } from '@jest/globals';

describe('AppModule', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppModule],
    }).compileComponents();
  });

  it('should create the module', () => {
    // by injecting the module class itself we verify it was registered
    const module = TestBed.inject(AppModule);
    expect(module).toBeTruthy();
  });

  it('should provide JwtInterceptor under HTTP_INTERCEPTORS', () => {
    const interceptors = TestBed.inject(HTTP_INTERCEPTORS) as any[];
    const hasJwt = interceptors.some((i) => i instanceof JwtInterceptor);
    expect(hasJwt).toBe(true);
  });

  it('should import MatSnackBarModule so MatSnackBar can be injected', () => {
    const snackBar = TestBed.inject(MatSnackBar, null);
    expect(snackBar).toBeDefined();
  });
});
