import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of } from 'rxjs';

import { AppComponent } from './app.component';
import { SessionService } from './services/session.service';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let sessionService: SessionService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, MatToolbarModule],
      declarations: [AppComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  describe('$isLogged', () => {
    it('should return observable from sessionService.$isLogged', () => {
      const mockIsLogged = of(true);
      jest.spyOn(sessionService, '$isLogged').mockReturnValue(mockIsLogged);

      const result = component.$isLogged();

      expect(result).toBe(mockIsLogged);
      expect(sessionService.$isLogged).toHaveBeenCalled();
    });
  });

  describe('logout', () => {
    it('should call sessionService.logOut and navigate to root', () => {
      jest.spyOn(sessionService, 'logOut');
      jest.spyOn(router, 'navigate');

      component.logout();

      expect(sessionService.logOut).toHaveBeenCalled();
      expect(router.navigate).toHaveBeenCalledWith(['']);
    });
  });
});
