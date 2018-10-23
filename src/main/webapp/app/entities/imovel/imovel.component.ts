import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImovel } from 'app/shared/model/imovel.model';
import { Principal } from 'app/core';
import { ImovelService } from './imovel.service';

@Component({
    selector: 'jhi-imovel',
    templateUrl: './imovel.component.html'
})
export class ImovelComponent implements OnInit, OnDestroy {
    imovels: IImovel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private imovelService: ImovelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.imovelService.query().subscribe(
            (res: HttpResponse<IImovel[]>) => {
                this.imovels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInImovels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IImovel) {
        return item.id;
    }

    registerChangeInImovels() {
        this.eventSubscriber = this.eventManager.subscribe('imovelListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
