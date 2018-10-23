import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVistoria } from 'app/shared/model/vistoria.model';

@Component({
    selector: 'jhi-vistoria-detail',
    templateUrl: './vistoria-detail.component.html'
})
export class VistoriaDetailComponent implements OnInit {
    vistoria: IVistoria;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vistoria }) => {
            this.vistoria = vistoria;
        });
    }

    previousState() {
        window.history.back();
    }
}
