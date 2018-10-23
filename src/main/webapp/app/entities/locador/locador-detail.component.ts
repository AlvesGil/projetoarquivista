import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocador } from 'app/shared/model/locador.model';

@Component({
    selector: 'jhi-locador-detail',
    templateUrl: './locador-detail.component.html'
})
export class LocadorDetailComponent implements OnInit {
    locador: ILocador;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ locador }) => {
            this.locador = locador;
        });
    }

    previousState() {
        window.history.back();
    }
}
