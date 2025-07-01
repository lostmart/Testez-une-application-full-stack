import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

import { expect, it, describe, beforeEach } from '@jest/globals';

describe('AuthRoutingModule', () => {
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        // start with an empty root config
        RouterTestingModule.withRoutes([]),
        // then register our child routes
        AuthRoutingModule,
      ],
    }).compileComponents();

    router = TestBed.inject(Router);
  });

  it('should create the routing module', () => {
    const module = TestBed.inject(AuthRoutingModule);
    expect(module).toBeTruthy();
  });

  it('should register the /login route', () => {
    const loginRoute = router.config.find((r) => r.path === 'login');
    expect(loginRoute).toBeDefined();
    expect(loginRoute!.component).toBe(LoginComponent);
    expect(loginRoute!.title).toBe('Login');
  });

  it('should register the /register route', () => {
    const registerRoute = router.config.find((r) => r.path === 'register');
    expect(registerRoute).toBeDefined();
    expect(registerRoute!.component).toBe(RegisterComponent);
    expect(registerRoute!.title).toBe('Register');
  });
});
