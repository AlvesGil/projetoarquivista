import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArquivista } from 'app/shared/model/arquivista.model';

@Component({
    selector: 'jhi-arquivista-detail',
    templateUrl: './arquivista-detail.component.html'
})
export class ArquivistaDetailComponent implements OnInit {
    arquivista: IArquivista;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ arquivista }) => {
            this.arquivista = arquivista;
        });
    }

    previousState() {
        window.history.back();
    }
}
