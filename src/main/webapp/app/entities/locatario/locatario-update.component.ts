import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILocatario } from 'app/shared/model/locatario.model';
import { LocatarioService } from './locatario.service';
import { IArquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from 'app/entities/arquivista';

@Component({
    selector: 'jhi-locatario-update',
    templateUrl: './locatario-update.component.html'
})
export class LocatarioUpdateComponent implements OnInit {
    locatario: ILocatario;
    isSaving: boolean;

    arquivistas: IArquivista[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private locatarioService: LocatarioService,
        private arquivistaService: ArquivistaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ locatario }) => {
            this.locatario = locatario;
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
        if (this.locatario.id !== undefined) {
            this.subscribeToSaveResponse(this.locatarioService.update(this.locatario));
        } else {
            this.subscribeToSaveResponse(this.locatarioService.create(this.locatario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILocatario>>) {
        result.subscribe((res: HttpResponse<ILocatario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
