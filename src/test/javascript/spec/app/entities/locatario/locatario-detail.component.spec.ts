/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { LocatarioDetailComponent } from 'app/entities/locatario/locatario-detail.component';
import { Locatario } from 'app/shared/model/locatario.model';

describe('Component Tests', () => {
    describe('Locatario Management Detail Component', () => {
        let comp: LocatarioDetailComponent;
        let fixture: ComponentFixture<LocatarioDetailComponent>;
        const route = ({ data: of({ locatario: new Locatario(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LocatarioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LocatarioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LocatarioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.locatario).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
