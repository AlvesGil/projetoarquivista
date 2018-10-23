/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { ArquivistaUpdateComponent } from 'app/entities/arquivista/arquivista-update.component';
import { ArquivistaService } from 'app/entities/arquivista/arquivista.service';
import { Arquivista } from 'app/shared/model/arquivista.model';

describe('Component Tests', () => {
    describe('Arquivista Management Update Component', () => {
        let comp: ArquivistaUpdateComponent;
        let fixture: ComponentFixture<ArquivistaUpdateComponent>;
        let service: ArquivistaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [ArquivistaUpdateComponent]
            })
                .overrideTemplate(ArquivistaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ArquivistaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArquivistaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Arquivista(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.arquivista = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Arquivista();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.arquivista = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
