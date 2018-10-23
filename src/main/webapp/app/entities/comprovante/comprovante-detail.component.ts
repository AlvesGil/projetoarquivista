import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComprovante } from 'app/shared/model/comprovante.model';

@Component({
    selector: 'jhi-comprovante-detail',
    templateUrl: './comprovante-detail.component.html'
})
export class ComprovanteDetailComponent implements OnInit {
    comprovante: IComprovante;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ comprovante }) => {
            this.comprovante = comprovante;
        });
    }

    previousState() {
        window.history.back();
    }
}
