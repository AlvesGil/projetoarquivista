import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImovel } from 'app/shared/model/imovel.model';

@Component({
    selector: 'jhi-imovel-detail',
    templateUrl: './imovel-detail.component.html'
})
export class ImovelDetailComponent implements OnInit {
    imovel: IImovel;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ imovel }) => {
            this.imovel = imovel;
        });
    }

    previousState() {
        window.history.back();
    }
}
