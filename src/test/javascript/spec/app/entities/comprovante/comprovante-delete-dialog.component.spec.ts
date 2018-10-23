/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArquivistaTestModule } from '../../../test.module';
import { ComprovanteDeleteDialogComponent } from 'app/entities/comprovante/comprovante-delete-dialog.component';
import { ComprovanteService } from 'app/entities/comprovante/comprovante.service';

describe('Component Tests', () => {
    describe('Comprovante Management Delete Component', () => {
        let comp: ComprovanteDeleteDialogComponent;
        let fixture: ComponentFixture<ComprovanteDeleteDialogComponent>;
        let service: ComprovanteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [ComprovanteDeleteDialogComponent]
            })
                .overrideTemplate(ComprovanteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ComprovanteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComprovanteService);
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
