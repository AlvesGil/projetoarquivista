import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IImovel } from 'app/shared/model/imovel.model';
import { ImovelService } from './imovel.service';
import { IVistoria } from 'app/shared/model/vistoria.model';
import { VistoriaService } from 'app/entities/vistoria';
import { IArquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from 'app/entities/arquivista';

@Component({
    selector: 'jhi-imovel-update',
    templateUrl: './imovel-update.component.html'
})
export class ImovelUpdateComponent implements OnInit {
    imovel: IImovel;
    isSaving: boolean;

    vistorias: IVistoria[];

    arquivistas: IArquivista[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private imovelService: ImovelService,
        private vistoriaService: VistoriaService,
        private arquivistaService: ArquivistaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ imovel }) => {
            this.imovel = imovel;
        });
        this.vistoriaService.query().subscribe(
            (res: HttpResponse<IVistoria[]>) => {
                this.vistorias = res.body;
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
        if (this.imovel.id !== undefined) {
            this.subscribeToSaveResponse(this.imovelService.update(this.imovel));
        } else {
            this.subscribeToSaveResponse(this.imovelService.create(this.imovel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IImovel>>) {
        result.subscribe((res: HttpResponse<IImovel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVistoriaById(index: number, item: IVistoria) {
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
