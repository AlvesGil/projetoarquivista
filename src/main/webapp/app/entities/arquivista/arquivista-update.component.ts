import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IArquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from './arquivista.service';
import { ILocador } from 'app/shared/model/locador.model';
import { LocadorService } from 'app/entities/locador';
import { ILocatario } from 'app/shared/model/locatario.model';
import { LocatarioService } from 'app/entities/locatario';
import { IImovel } from 'app/shared/model/imovel.model';
import { ImovelService } from 'app/entities/imovel';
import { IPagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from 'app/entities/pagamento';
import { IComprovante } from 'app/shared/model/comprovante.model';
import { ComprovanteService } from 'app/entities/comprovante';
import { IVistoria } from 'app/shared/model/vistoria.model';
import { VistoriaService } from 'app/entities/vistoria';
import { ILogin } from 'app/shared/model/login.model';
import { LoginService } from 'app/entities/login';

@Component({
    selector: 'jhi-arquivista-update',
    templateUrl: './arquivista-update.component.html'
})
export class ArquivistaUpdateComponent implements OnInit {
    arquivista: IArquivista;
    isSaving: boolean;

    locadors: ILocador[];

    locatarios: ILocatario[];

    imovels: IImovel[];

    pagamentos: IPagamento[];

    comprovantes: IComprovante[];

    vistorias: IVistoria[];

    logins: ILogin[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private arquivistaService: ArquivistaService,
        private locadorService: LocadorService,
        private locatarioService: LocatarioService,
        private imovelService: ImovelService,
        private pagamentoService: PagamentoService,
        private comprovanteService: ComprovanteService,
        private vistoriaService: VistoriaService,
        private loginService: LoginService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ arquivista }) => {
            this.arquivista = arquivista;
        });
        this.locadorService.query().subscribe(
            (res: HttpResponse<ILocador[]>) => {
                this.locadors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.locatarioService.query().subscribe(
            (res: HttpResponse<ILocatario[]>) => {
                this.locatarios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.imovelService.query().subscribe(
            (res: HttpResponse<IImovel[]>) => {
                this.imovels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.pagamentoService.query().subscribe(
            (res: HttpResponse<IPagamento[]>) => {
                this.pagamentos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.comprovanteService.query().subscribe(
            (res: HttpResponse<IComprovante[]>) => {
                this.comprovantes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.vistoriaService.query().subscribe(
            (res: HttpResponse<IVistoria[]>) => {
                this.vistorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.loginService.query().subscribe(
            (res: HttpResponse<ILogin[]>) => {
                this.logins = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.arquivista.id !== undefined) {
            this.subscribeToSaveResponse(this.arquivistaService.update(this.arquivista));
        } else {
            this.subscribeToSaveResponse(this.arquivistaService.create(this.arquivista));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IArquivista>>) {
        result.subscribe((res: HttpResponse<IArquivista>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLocatarioById(index: number, item: ILocatario) {
        return item.id;
    }

    trackImovelById(index: number, item: IImovel) {
        return item.id;
    }

    trackPagamentoById(index: number, item: IPagamento) {
        return item.id;
    }

    trackComprovanteById(index: number, item: IComprovante) {
        return item.id;
    }

    trackVistoriaById(index: number, item: IVistoria) {
        return item.id;
    }

    trackLoginById(index: number, item: ILogin) {
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
