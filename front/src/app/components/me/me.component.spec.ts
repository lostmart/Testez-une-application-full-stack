import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { User } from '../../interfaces/user.interface';
import { SessionService } from '../../services/session.service';
import { UserService } from '../../services/user.service';
import { MeComponent } from './me.component';
import { expect, it, describe, beforeEach } from '@jest/globals';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userService: UserService;
  let sessionService: SessionService;
  let router: Router;
  let snackBar: MatSnackBar;

  const mockUser: User = {
    id: 1,
    email: 'test@test.com',
    lastName: 'Doe',
    firstName: 'John',
    admin: false,
    password: 'password',
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const mockAdminUser: User = {
    ...mockUser,
    admin: true,
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        MatCardModule,
        MatIconModule,
        MatSnackBarModule,
        NoopAnimationsModule,
      ],
      providers: [
        {
          provide: SessionService,
          useValue: {
            sessionInformation: {
              id: 1,
              admin: false,
            },
            logOut: jest.fn(),
          },
        },
        {
          provide: UserService,
          useValue: {
            getById: jest.fn().mockReturnValue(of(mockUser)),
            delete: jest.fn().mockReturnValue(of({})),
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    snackBar = TestBed.inject(MatSnackBar);

    jest.spyOn(router, 'navigate');
    jest.spyOn(snackBar, 'open');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should fetch user data on init', () => {
      component.ngOnInit();
      expect(userService.getById).toHaveBeenCalledWith('1');
      fixture.detectChanges();
      expect(component.user).toEqual(mockUser);
    });
  });

  describe('back', () => {
    it('should call window.history.back()', () => {
      jest.spyOn(window.history, 'back');
      component.back();
      expect(window.history.back).toHaveBeenCalled();
    });
  });

  describe('delete', () => {
    it('should delete account and redirect', (done) => {
      const navigateSpy = jest.spyOn(router, 'navigate');

      component.delete();

      // wait one tick so your subscribe() callback has definitely run
      setTimeout(() => {
        expect(userService.delete).toHaveBeenCalledWith('1');
        expect(snackBar.open).toHaveBeenCalledWith(
          'Your account has been deleted !',
          'Close',
          { duration: 3000 }
        );
        expect(navigateSpy).toHaveBeenCalledWith(['/']);
        done();
      }, 0);
    });
  });

  describe('template', () => {
    it('should show admin message for admin user', () => {
      (sessionService.sessionInformation as any).admin = true;
      jest.spyOn(userService, 'getById').mockReturnValue(of(mockAdminUser));

      component.ngOnInit();
      fixture.detectChanges();

      const adminMessage = fixture.nativeElement.querySelector('.my2');
      expect(adminMessage.textContent).toContain('You are admin');
    });

    it('should show delete button for non-admin user', () => {
      component.user = mockUser;
      fixture.detectChanges();

      const deleteButton = fixture.nativeElement.querySelector(
        'button[color="warn"]'
      );
      expect(deleteButton).toBeTruthy();
      expect(deleteButton.textContent).toContain('deleteDetail');
    });
  });
});
