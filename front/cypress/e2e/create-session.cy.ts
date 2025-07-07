/// <reference types="cypress" />

describe('Create Session Flow', () => {
  before(() => {
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
    const date = new Date();

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    const formattedDate = `${year}-${month}-${day}`;
    cy.get('input[formcontrolname="date"]').type(formattedDate);

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

  it('should open the edit form for the newly created session', () => {
    // Find the session card and click Edit
    cy.contains('mat-card', 'Cypress Yoga Session').contains('Edit').click();

    // Wait for the edit form to load
    cy.url().should('include', '/sessions/update');
    cy.get('input[formcontrolname="name"]').should(
      'have.value',
      'Cypress Yoga Session'
    );
    cy.url().should('include', '/sessions/update');

    //update description
    cy.get('textarea[formcontrolname="description"]')
      .clear()
      .type('This is a test changing the description with Cypress !!!');

    // Submit the form
    cy.get('button[type="submit"]').should('not.be.disabled').click();

    // Assert redirect and snackbar
    cy.url().should('include', '/sessions');
    cy.contains('Session updated !').should('exist');
  });

  it('should open the session details and check components', () => {
    // Find the session card and click Details
    cy.contains('mat-card', 'Cypress Yoga Session').contains('Detail').click();

    // Wait for the details form to load
    cy.url().should('include', '/sessions/detail');

    // Check for the image with alt="Yoga session"
    cy.get('img[alt="Yoga session"]').should('be.visible');

    cy.contains('mat-card', 'Cypress Yoga Session')
      .contains('Delete')
      .should('exist');

    cy.url().should('include', '/sessions/detail');

    // Check the icon and teacher name exist together
    cy.get('mat-card-subtitle').within(() => {
      cy.get('mat-icon').should('contain.text', 'people');
      cy.get('[data-cy-teacher]').should('exist'); 
      cy.get('[data-cy-teacher]').invoke('text').should('not.be.empty');
    });

    // Check the icon and number of attendees exist together
    cy.get('[data-cy-attendees]').within(() => {
      cy.get('mat-icon').should('contain.text', 'group');
      cy.get('span').should('contain.text', 'attendees');
    });

    cy.get('[data-cy-created]').within(() => {
      cy.get('mat-icon').should('contain.text', 'calendar_month');
      cy.get('span').invoke('text').should('not.be.empty');
    });

    // Ensure you're on the session detail page
    cy.url().should('include', '/sessions/detail');

    // Click the Delete button
    cy.contains('button', 'Delete').click();

    // Confirm redirect to sessions list
    cy.url().should('include', '/sessions');

    // Confirm snackbar shows deletion message
    cy.contains('Session deleted !').should('be.visible');
  });
});

// TO DO
// - arrange create and delete sessions
