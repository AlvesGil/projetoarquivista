/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { ArquivistaDetailComponent } from 'app/entities/arquivista/arquivista-detail.component';
import { Arquivista } from 'app/shared/model/arquivista.model';

describe('Component Tests', () => {
    describe('Arquivista Management Detail Component', () => {
        let comp: ArquivistaDetailComponent;
        let fixture: ComponentFixture<ArquivistaDetailComponent>;
        const route = ({ data: of({ arquivista: new Arquivista(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [ArquivistaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ArquivistaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ArquivistaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.arquivista).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
