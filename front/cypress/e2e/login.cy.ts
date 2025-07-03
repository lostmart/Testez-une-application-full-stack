/// <reference types="cypress" />

describe('Login spec', () => {
  it('Login successful', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true,
      },
    });

    cy.intercept('GET', '/api/session', []).as('session');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(`test!1234{enter}{enter}`);

    cy.url().should('include', '/sessions');
  });

  it('Login fails with invalid credentials', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: { message: 'Invalid credentials' },
    });

    cy.get('input[formControlName=email]').type('wrong@user.com');
    cy.get('input[formControlName=password]').type(`wrongpass{enter}{enter}`);

    cy.get('p.error').should('contain.text', 'An error occurred');
    cy.url().should('include', '/login');
  });
});
