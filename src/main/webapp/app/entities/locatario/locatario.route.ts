import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Locatario } from 'app/shared/model/locatario.model';
import { LocatarioService } from './locatario.service';
import { LocatarioComponent } from './locatario.component';
import { LocatarioDetailComponent } from './locatario-detail.component';
import { LocatarioUpdateComponent } from './locatario-update.component';
import { LocatarioDeletePopupComponent } from './locatario-delete-dialog.component';
import { ILocatario } from 'app/shared/model/locatario.model';

@Injectable({ providedIn: 'root' })
export class LocatarioResolve implements Resolve<ILocatario> {
    constructor(private service: LocatarioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((locatario: HttpResponse<Locatario>) => locatario.body));
        }
        return of(new Locatario());
    }
}

export const locatarioRoute: Routes = [
    {
        path: 'locatario',
        component: LocatarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locatarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'locatario/:id/view',
        component: LocatarioDetailComponent,
        resolve: {
            locatario: LocatarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locatarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'locatario/new',
        component: LocatarioUpdateComponent,
        resolve: {
            locatario: LocatarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locatarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'locatario/:id/edit',
        component: LocatarioUpdateComponent,
        resolve: {
            locatario: LocatarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locatarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const locatarioPopupRoute: Routes = [
    {
        path: 'locatario/:id/delete',
        component: LocatarioDeletePopupComponent,
        resolve: {
            locatario: LocatarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locatarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
