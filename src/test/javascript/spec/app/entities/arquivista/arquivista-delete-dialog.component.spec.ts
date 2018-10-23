/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArquivistaTestModule } from '../../../test.module';
import { ArquivistaDeleteDialogComponent } from 'app/entities/arquivista/arquivista-delete-dialog.component';
import { ArquivistaService } from 'app/entities/arquivista/arquivista.service';

describe('Component Tests', () => {
    describe('Arquivista Management Delete Component', () => {
        let comp: ArquivistaDeleteDialogComponent;
        let fixture: ComponentFixture<ArquivistaDeleteDialogComponent>;
        let service: ArquivistaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [ArquivistaDeleteDialogComponent]
            })
                .overrideTemplate(ArquivistaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ArquivistaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArquivistaService);
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
