import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of, throwError } from 'rxjs';
import { By } from '@angular/platform-browser';

import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

import { expect, it, describe, beforeEach } from '@jest/globals';


describe('RegisterComponent (Jest)', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: jest.Mocked<AuthService>;
  let router: jest.Mocked<Router>;

  beforeEach(async () => {
    authService = { register: jest.fn() } as any;
    router      = { navigate: jest.fn() } as any;

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientTestingModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatButtonModule  // for mat-raised-button
      ],
      providers: [
        { provide: AuthService, useValue: authService },
        { provide: Router,      useValue: router }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture   = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should enable submit button when form is valid', async () => {
    component.form.setValue({
      email:     'test@example.com',
      firstName: 'John',
      lastName:  'Doe',
      password:  '123456'
    });

    fixture.detectChanges();
    await fixture.whenStable();
    fixture.detectChanges();

    expect(component.form.valid).toBe(true);

    const btn = fixture.debugElement.query(By.css('button[type="submit"]'));
    expect(btn.nativeElement.disabled).toBe(false);
  });

  it('should call register and navigate on success', async () => {
    authService.register.mockReturnValue(of(void 0));

    component.form.setValue({
      email:     'test@example.com',
      firstName: 'John',
      lastName:  'Doe',
      password:  '123456'
    });

    fixture.detectChanges();
    await fixture.whenStable();

    component.submit();

    expect(authService.register).toHaveBeenCalledWith(component.form.value);
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
    expect(component.onError).toBe(false);
  });

  it('should set onError to true if register fails', async () => {
    authService.register.mockReturnValue(throwError(() => new Error('fail')));

    component.form.setValue({
      email:     'fail@example.com',
      firstName: 'Fail',
      lastName:  'User',
      password:  '123456'
    });

    fixture.detectChanges();
    await fixture.whenStable();

    component.submit();
    expect(component.onError).toBe(true);

    fixture.detectChanges();
    const err = fixture.debugElement.query(By.css('.error'));
    expect(err).toBeTruthy();
  });
});
