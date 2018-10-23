import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from './pagamento.service';
import { ILocador } from 'app/shared/model/locador.model';
import { LocadorService } from 'app/entities/locador';
import { IArquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from 'app/entities/arquivista';

@Component({
    selector: 'jhi-pagamento-update',
    templateUrl: './pagamento-update.component.html'
})
export class PagamentoUpdateComponent implements OnInit {
    pagamento: IPagamento;
    isSaving: boolean;

    locadors: ILocador[];

    arquivistas: IArquivista[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private pagamentoService: PagamentoService,
        private locadorService: LocadorService,
        private arquivistaService: ArquivistaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pagamento }) => {
            this.pagamento = pagamento;
        });
        this.locadorService.query().subscribe(
            (res: HttpResponse<ILocador[]>) => {
                this.locadors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.arquivistaService.query().subscribe(
            (res: HttpResponse<IArquivista[]>) => {
                this.arquivistas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pagamento.id !== undefined) {
            this.subscribeToSaveResponse(this.pagamentoService.update(this.pagamento));
        } else {
            this.subscribeToSaveResponse(this.pagamentoService.create(this.pagamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPagamento>>) {
        result.subscribe((res: HttpResponse<IPagamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLocadorById(index: number, item: ILocador) {
        return item.id;
    }

    trackArquivistaById(index: number, item: IArquivista) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
