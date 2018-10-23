import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComprovante } from 'app/shared/model/comprovante.model';
import { ComprovanteService } from './comprovante.service';

@Component({
    selector: 'jhi-comprovante-delete-dialog',
    templateUrl: './comprovante-delete-dialog.component.html'
})
export class ComprovanteDeleteDialogComponent {
    comprovante: IComprovante;

    constructor(
        private comprovanteService: ComprovanteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.comprovanteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'comprovanteListModification',
                content: 'Deleted an comprovante'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-comprovante-delete-popup',
    template: ''
})
export class ComprovanteDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ comprovante }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ComprovanteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.comprovante = comprovante;
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
