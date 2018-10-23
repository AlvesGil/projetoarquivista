/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { LoginUpdateComponent } from 'app/entities/login/login-update.component';
import { LoginService } from 'app/entities/login/login.service';
import { Login } from 'app/shared/model/login.model';

describe('Component Tests', () => {
    describe('Login Management Update Component', () => {
        let comp: LoginUpdateComponent;
        let fixture: ComponentFixture<LoginUpdateComponent>;
        let service: LoginService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LoginUpdateComponent]
            })
                .overrideTemplate(LoginUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoginUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoginService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Login(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.login = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Login();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.login = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
