/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArquivistaTestModule } from '../../../test.module';
import { LoginDeleteDialogComponent } from 'app/entities/login/login-delete-dialog.component';
import { LoginService } from 'app/entities/login/login.service';

describe('Component Tests', () => {
    describe('Login Management Delete Component', () => {
        let comp: LoginDeleteDialogComponent;
        let fixture: ComponentFixture<LoginDeleteDialogComponent>;
        let service: LoginService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArquivistaTestModule],
                declarations: [LoginDeleteDialogComponent]
            })
                .overrideTemplate(LoginDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoginDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoginService);
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
