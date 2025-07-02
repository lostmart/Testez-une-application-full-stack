/// <reference types="cypress" />

describe('Create Session Flow', () => {
  beforeEach(() => {
    // Login as admin user
    cy.visit('/login');
    cy.get('input[formcontrolname="email"]').type('yoga@studio.com');
    cy.get('input[formcontrolname="password"]').type('test!1234');
    cy.get('button[type="submit"]').click();

    // Wait for redirect to session list page
    cy.url().should('include', '/sessions');
  });

  it('should create a new session successfully', () => {
    // Click the "+ Create" button
    cy.contains('Create').click();

    // Fill in the form
    cy.get('input[formcontrolname="name"]').type('Cypress Yoga Session');
    cy.get('input[formcontrolname="date"]').type('2025-12-31');

    // Wait for teachers to load and select first one
    cy.get('mat-select[formcontrolname="teacher_id"]').click();
    cy.get('mat-option').first().click();

    cy.get('textarea[formcontrolname="description"]').type(
      'This is a test session created with Cypress.'
    );

    // Submit the form
    cy.get('button[type="submit"]').should('not.be.disabled').click();

    // Assert redirect and snackbar
    cy.url().should('include', '/sessions');
    cy.contains('Session created !').should('exist');
  });
});
