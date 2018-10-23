import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVistoria } from 'app/shared/model/vistoria.model';
import { Principal } from 'app/core';
import { VistoriaService } from './vistoria.service';

@Component({
    selector: 'jhi-vistoria',
    templateUrl: './vistoria.component.html'
})
export class VistoriaComponent implements OnInit, OnDestroy {
    vistorias: IVistoria[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private vistoriaService: VistoriaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.vistoriaService.query().subscribe(
            (res: HttpResponse<IVistoria[]>) => {
                this.vistorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVistorias();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVistoria) {
        return item.id;
    }

    registerChangeInVistorias() {
        this.eventSubscriber = this.eventManager.subscribe('vistoriaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
