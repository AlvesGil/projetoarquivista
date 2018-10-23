import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Comprovante } from 'app/shared/model/comprovante.model';
import { ComprovanteService } from './comprovante.service';
import { ComprovanteComponent } from './comprovante.component';
import { ComprovanteDetailComponent } from './comprovante-detail.component';
import { ComprovanteUpdateComponent } from './comprovante-update.component';
import { ComprovanteDeletePopupComponent } from './comprovante-delete-dialog.component';
import { IComprovante } from 'app/shared/model/comprovante.model';

@Injectable({ providedIn: 'root' })
export class ComprovanteResolve implements Resolve<IComprovante> {
    constructor(private service: ComprovanteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((comprovante: HttpResponse<Comprovante>) => comprovante.body));
        }
        return of(new Comprovante());
    }
}

export const comprovanteRoute: Routes = [
    {
        path: 'comprovante',
        component: ComprovanteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comprovantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comprovante/:id/view',
        component: ComprovanteDetailComponent,
        resolve: {
            comprovante: ComprovanteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comprovantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comprovante/new',
        component: ComprovanteUpdateComponent,
        resolve: {
            comprovante: ComprovanteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comprovantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'comprovante/:id/edit',
        component: ComprovanteUpdateComponent,
        resolve: {
            comprovante: ComprovanteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comprovantes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const comprovantePopupRoute: Routes = [
    {
        path: 'comprovante/:id/delete',
        component: ComprovanteDeletePopupComponent,
        resolve: {
            comprovante: ComprovanteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Comprovantes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
