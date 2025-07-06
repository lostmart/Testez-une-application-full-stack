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
});
