/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArquivistaTestModule } from '../../../test.module';
import { LocatarioComponent } from 'app/entities/locatario/locatario.component';
import { LocatarioService } from 'app/entities/locatario/locatario.service';
import { Locatario } from 'app/shared/model/locatario.model';

describe('Component Tests', () => {
    describe('Locatario Management Component', () => {
        let comp: LocatarioComponent;
        let fixture: ComponentFixture<LocatarioComponent>;
        let service: LocatarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LocatarioComponent],
                providers: []
            })
                .overrideTemplate(LocatarioComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocatarioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocatarioService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Locatario(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.locatarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
