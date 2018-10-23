import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILogin } from 'app/shared/model/login.model';
import { LoginService } from './login.service';

@Component({
    selector: 'jhi-login-delete-dialog',
    templateUrl: './login-delete-dialog.component.html'
})
export class LoginDeleteDialogComponent {
    login: ILogin;

    constructor(private loginService: LoginService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.loginService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'loginListModification',
                content: 'Deleted an login'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-login-delete-popup',
    template: ''
})
export class LoginDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ login }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LoginDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.login = login;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
