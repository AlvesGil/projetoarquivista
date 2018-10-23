import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IComprovante } from 'app/shared/model/comprovante.model';
import { Principal } from 'app/core';
import { ComprovanteService } from './comprovante.service';

@Component({
    selector: 'jhi-comprovante',
    templateUrl: './comprovante.component.html'
})
export class ComprovanteComponent implements OnInit, OnDestroy {
    comprovantes: IComprovante[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private comprovanteService: ComprovanteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.comprovanteService.query().subscribe(
            (res: HttpResponse<IComprovante[]>) => {
                this.comprovantes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInComprovantes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IComprovante) {
        return item.id;
    }

    registerChangeInComprovantes() {
        this.eventSubscriber = this.eventManager.subscribe('comprovanteListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
