/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArquivistaTestModule } from '../../../test.module';
import { LoginComponent } from 'app/entities/login/login.component';
import { LoginService } from 'app/entities/login/login.service';
import { Login } from 'app/shared/model/login.model';

describe('Component Tests', () => {
    describe('Login Management Component', () => {
        let comp: LoginComponent;
        let fixture: ComponentFixture<LoginComponent>;
        let service: LoginService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LoginComponent],
                providers: []
            })
                .overrideTemplate(LoginComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoginComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoginService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Login(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.logins[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
