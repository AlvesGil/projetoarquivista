/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { LoginDetailComponent } from 'app/entities/login/login-detail.component';
import { Login } from 'app/shared/model/login.model';

describe('Component Tests', () => {
    describe('Login Management Detail Component', () => {
        let comp: LoginDetailComponent;
        let fixture: ComponentFixture<LoginDetailComponent>;
        const route = ({ data: of({ login: new Login(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LoginDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LoginDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoginDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.login).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
