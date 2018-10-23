/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { VistoriaUpdateComponent } from 'app/entities/vistoria/vistoria-update.component';
import { VistoriaService } from 'app/entities/vistoria/vistoria.service';
import { Vistoria } from 'app/shared/model/vistoria.model';

describe('Component Tests', () => {
    describe('Vistoria Management Update Component', () => {
        let comp: VistoriaUpdateComponent;
        let fixture: ComponentFixture<VistoriaUpdateComponent>;
        let service: VistoriaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [VistoriaUpdateComponent]
            })
                .overrideTemplate(VistoriaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VistoriaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VistoriaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Vistoria(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vistoria = entity;
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
                    const entity = new Vistoria();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vistoria = entity;
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
