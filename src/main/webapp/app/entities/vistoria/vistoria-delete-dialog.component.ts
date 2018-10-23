import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVistoria } from 'app/shared/model/vistoria.model';
import { VistoriaService } from './vistoria.service';

@Component({
    selector: 'jhi-vistoria-delete-dialog',
    templateUrl: './vistoria-delete-dialog.component.html'
})
export class VistoriaDeleteDialogComponent {
    vistoria: IVistoria;

    constructor(private vistoriaService: VistoriaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vistoriaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vistoriaListModification',
                content: 'Deleted an vistoria'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vistoria-delete-popup',
    template: ''
})
export class VistoriaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vistoria }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VistoriaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.vistoria = vistoria;
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
