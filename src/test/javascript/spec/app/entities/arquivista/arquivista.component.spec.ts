/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArquivistaTestModule } from '../../../test.module';
import { ArquivistaComponent } from 'app/entities/arquivista/arquivista.component';
import { ArquivistaService } from 'app/entities/arquivista/arquivista.service';
import { Arquivista } from 'app/shared/model/arquivista.model';

describe('Component Tests', () => {
    describe('Arquivista Management Component', () => {
        let comp: ArquivistaComponent;
        let fixture: ComponentFixture<ArquivistaComponent>;
        let service: ArquivistaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [ArquivistaComponent],
                providers: []
            })
                .overrideTemplate(ArquivistaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ArquivistaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArquivistaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Arquivista(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.arquivistas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
