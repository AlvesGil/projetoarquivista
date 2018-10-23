import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPagamento } from 'app/shared/model/pagamento.model';
import { Principal } from 'app/core';
import { PagamentoService } from './pagamento.service';

@Component({
    selector: 'jhi-pagamento',
    templateUrl: './pagamento.component.html'
})
export class PagamentoComponent implements OnInit, OnDestroy {
    pagamentos: IPagamento[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pagamentoService: PagamentoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.pagamentoService.query().subscribe(
            (res: HttpResponse<IPagamento[]>) => {
                this.pagamentos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPagamentos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPagamento) {
        return item.id;
    }

    registerChangeInPagamentos() {
        this.eventSubscriber = this.eventManager.subscribe('pagamentoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
