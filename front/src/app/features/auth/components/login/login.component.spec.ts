import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { of, throwError } from 'rxjs';
import { By } from '@angular/platform-browser';

import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { SessionService } from 'src/app/services/session.service';

import { expect, it, describe, beforeEach } from '@jest/globals';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let router: Router;

  const mockSessionService = {
    logIn: jest.fn(),
  };

  const mockAuthService = {
    login: jest.fn(),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: SessionService, useValue: mockSessionService },
      ],
      imports: [
        RouterTestingModule.withRoutes([]),
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        FormsModule,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should have form invalid initially', () => {
    expect(component.form.invalid).toBe(true);
  });

  it('should show error message when login fails', async () => {
    component.form.setValue({ email: 'test@example.com', password: '123' });
    mockAuthService.login.mockReturnValue(
      throwError(() => new Error('Login failed'))
    );

    component.submit();
    fixture.detectChanges();

    await fixture.whenStable();
    fixture.detectChanges();

    const errorMsg = fixture.debugElement.query(By.css('.error'));
    expect(errorMsg).toBeTruthy();
    expect(component.onError).toBe(true);
  });

  it('should call login and navigate on success', async () => {
    const routerSpy = jest.spyOn(router, 'navigate');
    const mockSessionData = { token: 'abc123' };

    component.form.setValue({ email: 'test@example.com', password: '123' });
    mockAuthService.login.mockReturnValue(of(mockSessionData));

    component.submit();
    await fixture.whenStable();
    fixture.detectChanges();

    expect(mockSessionService.logIn).toHaveBeenCalledWith(mockSessionData);
    expect(routerSpy).toHaveBeenCalledWith(['/sessions']);
    expect(component.onError).toBe(false);
  });

  // it('should call login and navigate on success', fakeAsync(() => {
  //   const routerSpy = jest.spyOn(router, 'navigate');
  //   component.form.setValue({ email: 'test@example.com', password: '123' });
  //   const mockSessionData = { token: 'abc123' };
  //   mockAuthService.login.mockReturnValue(of(mockSessionData));

  //   component.submit();
  //   tick();
  //   fixture.detectChanges();

  //   expect(mockSessionService.logIn).toHaveBeenCalledWith(mockSessionData);
  //   expect(routerSpy).toHaveBeenCalledWith(['/sessions']);
  //   expect(component.onError).toBe(false);
  // }));

  it('should toggle password visibility', () => {
    const initialState = component.hide;
    component.hide = !initialState;
    fixture.detectChanges();

    const icon = fixture.debugElement.query(By.css('mat-icon')).nativeElement;
    expect(icon.textContent).toContain(
      initialState ? 'visibility' : 'visibility_off'
    );
  });

  it('should disable submit button when form is invalid', () => {
    const button = fixture.debugElement.query(
      By.css('button[type=submit]')
    ).nativeElement;
    expect(button.disabled).toBe(true);
  });

  it('should enable submit button when form is valid', () => {
    component.form.setValue({ email: 'valid@mail.com', password: '123' });
    fixture.detectChanges();

    const button = fixture.debugElement.query(
      By.css('button[type=submit]')
    ).nativeElement;
    expect(button.disabled).toBe(false);
  });
});
