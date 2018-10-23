import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Locador } from 'app/shared/model/locador.model';
import { LocadorService } from './locador.service';
import { LocadorComponent } from './locador.component';
import { LocadorDetailComponent } from './locador-detail.component';
import { LocadorUpdateComponent } from './locador-update.component';
import { LocadorDeletePopupComponent } from './locador-delete-dialog.component';
import { ILocador } from 'app/shared/model/locador.model';

@Injectable({ providedIn: 'root' })
export class LocadorResolve implements Resolve<ILocador> {
    constructor(private service: LocadorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((locador: HttpResponse<Locador>) => locador.body));
        }
        return of(new Locador());
    }
}

export const locadorRoute: Routes = [
    {
        path: 'locador',
        component: LocadorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locadors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'locador/:id/view',
        component: LocadorDetailComponent,
        resolve: {
            locador: LocadorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locadors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'locador/new',
        component: LocadorUpdateComponent,
        resolve: {
            locador: LocadorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locadors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'locador/:id/edit',
        component: LocadorUpdateComponent,
        resolve: {
            locador: LocadorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locadors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const locadorPopupRoute: Routes = [
    {
        path: 'locador/:id/delete',
        component: LocadorDeletePopupComponent,
        resolve: {
            locador: LocadorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locadors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
