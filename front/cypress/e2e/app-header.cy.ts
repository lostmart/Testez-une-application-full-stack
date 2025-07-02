/// <reference types="cypress" />

context('When logged in', () => {
  beforeEach(() => {
    cy.visit('/login');

    // Confirm we're really on the login page before proceeding
    cy.url().should('include', '/login');

    // More stable selectors
    cy.get('input[formcontrolname="email"]')
      .should('be.visible')
      .type('yoga@studio.com');
    cy.get('input[formcontrolname="password"]')
      .should('be.visible')
      .type('test!1234');

    cy.get('button[type="submit"]').click();

    // Wait for redirection to confirm login worked
    cy.url().should('not.include', '/login');
  });

  it('should show Sessions, Account, and Logout links only', () => {
    cy.contains('Sessions').should('be.visible');
    cy.contains('Account').should('be.visible');
    cy.contains('Logout').should('be.visible');
    cy.contains('Login').should('not.exist');
    cy.contains('Register').should('not.exist');
  });

  it('should logout and return to home page', () => {
    cy.contains('Logout').click();
    cy.url().should('eq', Cypress.config().baseUrl + '/');
    cy.contains('Login').should('be.visible');
  });
});
