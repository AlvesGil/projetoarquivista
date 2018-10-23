import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVistoria } from 'app/shared/model/vistoria.model';
import { VistoriaService } from './vistoria.service';
import { IArquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from 'app/entities/arquivista';

@Component({
    selector: 'jhi-vistoria-update',
    templateUrl: './vistoria-update.component.html'
})
export class VistoriaUpdateComponent implements OnInit {
    vistoria: IVistoria;
    isSaving: boolean;

    arquivistas: IArquivista[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private vistoriaService: VistoriaService,
        private arquivistaService: ArquivistaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vistoria }) => {
            this.vistoria = vistoria;
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
        if (this.vistoria.id !== undefined) {
            this.subscribeToSaveResponse(this.vistoriaService.update(this.vistoria));
        } else {
            this.subscribeToSaveResponse(this.vistoriaService.create(this.vistoria));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVistoria>>) {
        result.subscribe((res: HttpResponse<IVistoria>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
