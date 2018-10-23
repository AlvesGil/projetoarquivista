/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { LocadorDetailComponent } from 'app/entities/locador/locador-detail.component';
import { Locador } from 'app/shared/model/locador.model';

describe('Component Tests', () => {
    describe('Locador Management Detail Component', () => {
        let comp: LocadorDetailComponent;
        let fixture: ComponentFixture<LocadorDetailComponent>;
        const route = ({ data: of({ locador: new Locador(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LocadorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LocadorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LocadorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.locador).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
