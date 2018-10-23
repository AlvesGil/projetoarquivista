import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILogin } from 'app/shared/model/login.model';
import { LoginService } from './login.service';
import { IArquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from 'app/entities/arquivista';
import { ILocador } from 'app/shared/model/locador.model';
import { LocadorService } from 'app/entities/locador';

@Component({
    selector: 'jhi-login-update',
    templateUrl: './login-update.component.html'
})
export class LoginUpdateComponent implements OnInit {
    login: ILogin;
    isSaving: boolean;

    arquivistas: IArquivista[];

    locadors: ILocador[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private loginService: LoginService,
        private arquivistaService: ArquivistaService,
        private locadorService: LocadorService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ login }) => {
            this.login = login;
        });
        this.arquivistaService.query({ filter: 'login-is-null' }).subscribe(
            (res: HttpResponse<IArquivista[]>) => {
                if (!this.login.arquivista || !this.login.arquivista.id) {
                    this.arquivistas = res.body;
                } else {
                    this.arquivistaService.find(this.login.arquivista.id).subscribe(
                        (subRes: HttpResponse<IArquivista>) => {
                            this.arquivistas = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.locadorService.query({ filter: 'login-is-null' }).subscribe(
            (res: HttpResponse<ILocador[]>) => {
                if (!this.login.locador || !this.login.locador.id) {
                    this.locadors = res.body;
                } else {
                    this.locadorService.find(this.login.locador.id).subscribe(
                        (subRes: HttpResponse<ILocador>) => {
                            this.locadors = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.login.id !== undefined) {
            this.subscribeToSaveResponse(this.loginService.update(this.login));
        } else {
            this.subscribeToSaveResponse(this.loginService.create(this.login));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILogin>>) {
        result.subscribe((res: HttpResponse<ILogin>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLocadorById(index: number, item: ILocador) {
        return item.id;
    }
}
