/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArquivistaTestModule } from '../../../test.module';
import { LocatarioDeleteDialogComponent } from 'app/entities/locatario/locatario-delete-dialog.component';
import { LocatarioService } from 'app/entities/locatario/locatario.service';

describe('Component Tests', () => {
    describe('Locatario Management Delete Component', () => {
        let comp: LocatarioDeleteDialogComponent;
        let fixture: ComponentFixture<LocatarioDeleteDialogComponent>;
        let service: LocatarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LocatarioDeleteDialogComponent]
            })
                .overrideTemplate(LocatarioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LocatarioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocatarioService);
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
