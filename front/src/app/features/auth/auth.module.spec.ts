import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';

import { AuthModule } from './auth.module';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';

import { expect, it, describe, beforeEach } from '@jest/globals';


describe('AuthModule', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        AuthModule,
        // Provide HttpClient so AuthService (if providedIn root) can be instantiated
        HttpClientTestingModule
      ]
    }).compileComponents();
  });

  it('should create the module', () => {
    const module = TestBed.inject(AuthModule);
    expect(module).toBeTruthy();
  });

  it('should be able to instantiate RegisterComponent', () => {
    const fixture = TestBed.createComponent(RegisterComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should be able to instantiate LoginComponent', () => {
    const fixture = TestBed.createComponent(LoginComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should provide FormBuilder (ReactiveFormsModule)', () => {
    const fb = TestBed.inject(FormBuilder);
    expect(fb).toBeDefined();
  });
});
