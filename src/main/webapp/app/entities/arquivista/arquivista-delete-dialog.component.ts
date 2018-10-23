import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from './arquivista.service';

@Component({
    selector: 'jhi-arquivista-delete-dialog',
    templateUrl: './arquivista-delete-dialog.component.html'
})
export class ArquivistaDeleteDialogComponent {
    arquivista: IArquivista;

    constructor(private arquivistaService: ArquivistaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.arquivistaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'arquivistaListModification',
                content: 'Deleted an arquivista'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-arquivista-delete-popup',
    template: ''
})
export class ArquivistaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ arquivista }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ArquivistaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.arquivista = arquivista;
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
