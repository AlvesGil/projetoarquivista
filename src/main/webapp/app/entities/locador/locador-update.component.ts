import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILocador } from 'app/shared/model/locador.model';
import { LocadorService } from './locador.service';
import { IImovel } from 'app/shared/model/imovel.model';
import { ImovelService } from 'app/entities/imovel';
import { ILogin } from 'app/shared/model/login.model';
import { LoginService } from 'app/entities/login';
import { IArquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from 'app/entities/arquivista';

@Component({
    selector: 'jhi-locador-update',
    templateUrl: './locador-update.component.html'
})
export class LocadorUpdateComponent implements OnInit {
    locador: ILocador;
    isSaving: boolean;

    imovels: IImovel[];

    logins: ILogin[];

    arquivistas: IArquivista[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private locadorService: LocadorService,
        private imovelService: ImovelService,
        private loginService: LoginService,
        private arquivistaService: ArquivistaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ locador }) => {
            this.locador = locador;
        });
        this.imovelService.query().subscribe(
            (res: HttpResponse<IImovel[]>) => {
                this.imovels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.loginService.query().subscribe(
            (res: HttpResponse<ILogin[]>) => {
                this.logins = res.body;
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
        if (this.locador.id !== undefined) {
            this.subscribeToSaveResponse(this.locadorService.update(this.locador));
        } else {
            this.subscribeToSaveResponse(this.locadorService.create(this.locador));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILocador>>) {
        result.subscribe((res: HttpResponse<ILocador>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackImovelById(index: number, item: IImovel) {
        return item.id;
    }

    trackLoginById(index: number, item: ILogin) {
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
