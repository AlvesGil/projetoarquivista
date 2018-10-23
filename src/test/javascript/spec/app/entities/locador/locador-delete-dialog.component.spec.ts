/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArquivistaTestModule } from '../../../test.module';
import { LocadorDeleteDialogComponent } from 'app/entities/locador/locador-delete-dialog.component';
import { LocadorService } from 'app/entities/locador/locador.service';

describe('Component Tests', () => {
    describe('Locador Management Delete Component', () => {
        let comp: LocadorDeleteDialogComponent;
        let fixture: ComponentFixture<LocadorDeleteDialogComponent>;
        let service: LocadorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LocadorDeleteDialogComponent]
            })
                .overrideTemplate(LocadorDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LocadorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocadorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
