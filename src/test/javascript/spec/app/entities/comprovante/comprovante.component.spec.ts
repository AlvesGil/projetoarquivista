/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArquivistaTestModule } from '../../../test.module';
import { ComprovanteComponent } from 'app/entities/comprovante/comprovante.component';
import { ComprovanteService } from 'app/entities/comprovante/comprovante.service';
import { Comprovante } from 'app/shared/model/comprovante.model';

describe('Component Tests', () => {
    describe('Comprovante Management Component', () => {
        let comp: ComprovanteComponent;
        let fixture: ComponentFixture<ComprovanteComponent>;
        let service: ComprovanteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [ComprovanteComponent],
                providers: []
            })
                .overrideTemplate(ComprovanteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ComprovanteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComprovanteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Comprovante(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.comprovantes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
