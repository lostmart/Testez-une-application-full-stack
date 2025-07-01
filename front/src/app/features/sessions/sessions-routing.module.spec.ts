import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { SessionsRoutingModule } from './sessions-routing.module';
import { ListComponent } from './components/list/list.component';
import { DetailComponent } from './components/detail/detail.component';
import { FormComponent } from './components/form/form.component';

import { expect, it, describe, beforeEach } from '@jest/globals';


describe('SessionsRoutingModule', () => {
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([]), SessionsRoutingModule],
    }).compileComponents();

    router = TestBed.inject(Router);
  });

  it('should create the routing module', () => {
    const module = TestBed.inject(SessionsRoutingModule);
    expect(module).toBeTruthy();
  });

  it('should register the default "" route to ListComponent', () => {
    const route = router.config.find((r) => r.path === '');
    expect(route).toBeDefined();
    expect(route!.component).toBe(ListComponent);
    expect(route!.title).toBe('Sessions');
  });

  it('should register "detail/:id" to DetailComponent', () => {
    const route = router.config.find((r) => r.path === 'detail/:id');
    expect(route).toBeDefined();
    expect(route!.component).toBe(DetailComponent);
    expect(route!.title).toBe('Sessions - detail');
  });

  it('should register "create" to FormComponent', () => {
    const route = router.config.find((r) => r.path === 'create');
    expect(route).toBeDefined();
    expect(route!.component).toBe(FormComponent);
    expect(route!.title).toBe('Sessions - create');
  });

  it('should register "update/:id" to FormComponent', () => {
    const route = router.config.find((r) => r.path === 'update/:id');
    expect(route).toBeDefined();
    expect(route!.component).toBe(FormComponent);
    expect(route!.title).toBe('Sessions - update');
  });
});
