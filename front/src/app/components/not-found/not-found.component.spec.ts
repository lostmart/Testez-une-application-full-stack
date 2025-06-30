import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { NotFoundComponent } from './not-found.component';
import { expect, it, describe, beforeEach } from '@jest/globals';

describe('NotFoundComponent', () => {
  let component: NotFoundComponent;
  let fixture: ComponentFixture<NotFoundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotFoundComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NotFoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render "Page not found !" text', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const h1 = compiled.querySelector('h1');
    expect(h1?.textContent?.trim()).toBe('Page not found !');
  });

  it('should have a div with class "flex justify-center mt3"', () => {
    const div = fixture.debugElement.query(
      By.css('div.flex.justify-center.mt3')
    );
    expect(div).toBeTruthy();
  });

  it('should contain only one h1 tag', () => {
    const h1Elements = fixture.nativeElement.querySelectorAll('h1');
    expect(h1Elements.length).toBe(1);
  });

  it('should not have any interactive elements', () => {
    const buttons = fixture.nativeElement.querySelectorAll('button, a, input');
    expect(buttons.length).toBe(0);
  });
});
