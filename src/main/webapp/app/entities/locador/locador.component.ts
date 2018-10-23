import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILocador } from 'app/shared/model/locador.model';
import { Principal } from 'app/core';
import { LocadorService } from './locador.service';

@Component({
    selector: 'jhi-locador',
    templateUrl: './locador.component.html'
})
export class LocadorComponent implements OnInit, OnDestroy {
    locadors: ILocador[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private locadorService: LocadorService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.locadorService.query().subscribe(
            (res: HttpResponse<ILocador[]>) => {
                this.locadors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLocadors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILocador) {
        return item.id;
    }

    registerChangeInLocadors() {
        this.eventSubscriber = this.eventManager.subscribe('locadorListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
