/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArquivistaTestModule } from '../../../test.module';
import { LocadorComponent } from 'app/entities/locador/locador.component';
import { LocadorService } from 'app/entities/locador/locador.service';
import { Locador } from 'app/shared/model/locador.model';

describe('Component Tests', () => {
    describe('Locador Management Component', () => {
        let comp: LocadorComponent;
        let fixture: ComponentFixture<LocadorComponent>;
        let service: LocadorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LocadorComponent],
                providers: []
            })
                .overrideTemplate(LocadorComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocadorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocadorService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Locador(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.locadors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
