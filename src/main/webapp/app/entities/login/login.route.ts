import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Login } from 'app/shared/model/login.model';
import { LoginService } from './login.service';
import { LoginComponent } from './login.component';
import { LoginDetailComponent } from './login-detail.component';
import { LoginUpdateComponent } from './login-update.component';
import { LoginDeletePopupComponent } from './login-delete-dialog.component';
import { ILogin } from 'app/shared/model/login.model';

@Injectable({ providedIn: 'root' })
export class LoginResolve implements Resolve<ILogin> {
    constructor(private service: LoginService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((login: HttpResponse<Login>) => login.body));
        }
        return of(new Login());
    }
}

export const loginRoute: Routes = [
    {
        path: 'login',
        component: LoginComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'login/:id/view',
        component: LoginDetailComponent,
        resolve: {
            login: LoginResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'login/new',
        component: LoginUpdateComponent,
        resolve: {
            login: LoginResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logins'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'login/:id/edit',
        component: LoginUpdateComponent,
        resolve: {
            login: LoginResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logins'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const loginPopupRoute: Routes = [
    {
        path: 'login/:id/delete',
        component: LoginDeletePopupComponent,
        resolve: {
            login: LoginResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Logins'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
