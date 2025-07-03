/// <reference types="cypress" />

describe('thisis a mart test from mart learning stuff', () => {
  it('passes', () => {
    cy.visit('/');
    cy.get('mat-toolbar').contains('span', 'Yoga app').should('be.visible');
  });
});
