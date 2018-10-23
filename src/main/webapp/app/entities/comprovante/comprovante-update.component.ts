import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IComprovante } from 'app/shared/model/comprovante.model';
import { ComprovanteService } from './comprovante.service';
import { IArquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from 'app/entities/arquivista';

@Component({
    selector: 'jhi-comprovante-update',
    templateUrl: './comprovante-update.component.html'
})
export class ComprovanteUpdateComponent implements OnInit {
    comprovante: IComprovante;
    isSaving: boolean;

    arquivistas: IArquivista[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private comprovanteService: ComprovanteService,
        private arquivistaService: ArquivistaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comprovante }) => {
            this.comprovante = comprovante;
        });
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
        if (this.comprovante.id !== undefined) {
            this.subscribeToSaveResponse(this.comprovanteService.update(this.comprovante));
        } else {
            this.subscribeToSaveResponse(this.comprovanteService.create(this.comprovante));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IComprovante>>) {
        result.subscribe((res: HttpResponse<IComprovante>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
