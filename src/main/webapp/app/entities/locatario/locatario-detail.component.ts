import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocatario } from 'app/shared/model/locatario.model';

@Component({
    selector: 'jhi-locatario-detail',
    templateUrl: './locatario-detail.component.html'
})
export class LocatarioDetailComponent implements OnInit {
    locatario: ILocatario;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ locatario }) => {
            this.locatario = locatario;
        });
    }

    previousState() {
        window.history.back();
    }
}
