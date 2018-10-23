/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { LocadorUpdateComponent } from 'app/entities/locador/locador-update.component';
import { LocadorService } from 'app/entities/locador/locador.service';
import { Locador } from 'app/shared/model/locador.model';

describe('Component Tests', () => {
    describe('Locador Management Update Component', () => {
        let comp: LocadorUpdateComponent;
        let fixture: ComponentFixture<LocadorUpdateComponent>;
        let service: LocadorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LocadorUpdateComponent]
            })
                .overrideTemplate(LocadorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocadorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocadorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Locador(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.locador = entity;
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
                    const entity = new Locador();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.locador = entity;
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
