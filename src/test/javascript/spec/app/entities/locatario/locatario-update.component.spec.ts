/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ArquivistaTestModule } from '../../../test.module';
import { LocatarioUpdateComponent } from 'app/entities/locatario/locatario-update.component';
import { LocatarioService } from 'app/entities/locatario/locatario.service';
import { Locatario } from 'app/shared/model/locatario.model';

describe('Component Tests', () => {
    describe('Locatario Management Update Component', () => {
        let comp: LocatarioUpdateComponent;
        let fixture: ComponentFixture<LocatarioUpdateComponent>;
        let service: LocatarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LocatarioUpdateComponent]
            })
                .overrideTemplate(LocatarioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocatarioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocatarioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Locatario(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.locatario = entity;
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
                    const entity = new Locatario();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.locatario = entity;
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
