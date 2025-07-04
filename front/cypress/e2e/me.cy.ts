/// <reference types="cypress" />

describe('Account Page (/me)', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[formcontrolname="email"]').type('yoga@studio.com');
    cy.get('input[formcontrolname="password"]').type('test!1234');
    cy.get('button[type="submit"]').click();
    cy.url().should('not.include', '/login');
    cy.contains('Account').click();
    cy.url().should('include', '/me');
  });

  it('should display user name and email loaded from session and API', () => {
    cy.contains('Name: Admin ADMIN').should('be.visible');
    cy.contains('Email: yoga@studio.com').should('be.visible');
    cy.contains('You are admin').should('be.visible');
  });

  // it('should go back when clicking the back button', () => {
  //   cy.go('forward'); // simulate history
  //   cy.get('button').contains('Back').click(); // update selector if necessary
  //   cy.url().should('not.include', '/me');
  // });
});
