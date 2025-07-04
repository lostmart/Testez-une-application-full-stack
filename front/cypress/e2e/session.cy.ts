import { SessionService } from '../../src/app/services/session.service';
import { AppComponent } from './../../src/app/app.component';
/// <reference types="cypress" />

describe('SessionService E2E Behavior', () => {
  it('should login and access protected route', () => {
    cy.visit('/login');

    // Simulate backend response for login
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'testuser',
        firstName: 'Test',
        lastName: 'User',
        admin: false,
      },
    }).as('loginRequest');

    // Simulate backend response for /api/session after login
    cy.intercept('GET', '/api/session', []).as('session');

    // Fill form and submit
    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234{enter}');

    // Wait for login to happen
    cy.wait('@loginRequest');

    // User should be redirected to /sessions
    cy.url().should('include', '/sessions');
  });

  it('should logout and be redirected to login', () => {
    // Login first
    cy.visit('/login');
    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234{enter}');

    // Ensure login success
    cy.url().should('include', '/sessions');

    // Now click logout
    cy.contains('Logout').click();

    // Should go to login page
    cy.url().should('include', '/login');
  });

  it('should not allow access to /me if not logged in', () => {
    cy.clearCookies();
    cy.visit('/me');

    // Expect redirect to login
    cy.url().should('include', '/login');
  });
});
