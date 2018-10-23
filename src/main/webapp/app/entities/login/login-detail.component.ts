import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogin } from 'app/shared/model/login.model';

@Component({
    selector: 'jhi-login-detail',
    templateUrl: './login-detail.component.html'
})
export class LoginDetailComponent implements OnInit {
    login: ILogin;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ login }) => {
            this.login = login;
        });
    }

    previousState() {
        window.history.back();
    }
}
