/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArquivistaTestModule } from '../../../test.module';
import { VistoriaComponent } from 'app/entities/vistoria/vistoria.component';
import { VistoriaService } from 'app/entities/vistoria/vistoria.service';
import { Vistoria } from 'app/shared/model/vistoria.model';

describe('Component Tests', () => {
    describe('Vistoria Management Component', () => {
        let comp: VistoriaComponent;
        let fixture: ComponentFixture<VistoriaComponent>;
        let service: VistoriaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [VistoriaComponent],
                providers: []
            })
                .overrideTemplate(VistoriaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VistoriaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VistoriaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Vistoria(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.vistorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
