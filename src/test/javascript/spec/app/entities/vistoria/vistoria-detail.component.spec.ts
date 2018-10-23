/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { VistoriaDetailComponent } from 'app/entities/vistoria/vistoria-detail.component';
import { Vistoria } from 'app/shared/model/vistoria.model';

describe('Component Tests', () => {
    describe('Vistoria Management Detail Component', () => {
        let comp: VistoriaDetailComponent;
        let fixture: ComponentFixture<VistoriaDetailComponent>;
        const route = ({ data: of({ vistoria: new Vistoria(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [VistoriaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VistoriaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VistoriaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vistoria).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
