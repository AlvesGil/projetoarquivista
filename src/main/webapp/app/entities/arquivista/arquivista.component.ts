import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IArquivista } from 'app/shared/model/arquivista.model';
import { Principal } from 'app/core';
import { ArquivistaService } from './arquivista.service';

@Component({
    selector: 'jhi-arquivista',
    templateUrl: './arquivista.component.html'
})
export class ArquivistaComponent implements OnInit, OnDestroy {
    arquivistas: IArquivista[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private arquivistaService: ArquivistaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.arquivistaService.query().subscribe(
            (res: HttpResponse<IArquivista[]>) => {
                this.arquivistas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInArquivistas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IArquivista) {
        return item.id;
    }

    registerChangeInArquivistas() {
        this.eventSubscriber = this.eventManager.subscribe('arquivistaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
