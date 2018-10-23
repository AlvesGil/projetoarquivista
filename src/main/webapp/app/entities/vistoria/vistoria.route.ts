import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Vistoria } from 'app/shared/model/vistoria.model';
import { VistoriaService } from './vistoria.service';
import { VistoriaComponent } from './vistoria.component';
import { VistoriaDetailComponent } from './vistoria-detail.component';
import { VistoriaUpdateComponent } from './vistoria-update.component';
import { VistoriaDeletePopupComponent } from './vistoria-delete-dialog.component';
import { IVistoria } from 'app/shared/model/vistoria.model';

@Injectable({ providedIn: 'root' })
export class VistoriaResolve implements Resolve<IVistoria> {
    constructor(private service: VistoriaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((vistoria: HttpResponse<Vistoria>) => vistoria.body));
        }
        return of(new Vistoria());
    }
}

export const vistoriaRoute: Routes = [
    {
        path: 'vistoria',
        component: VistoriaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vistorias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vistoria/:id/view',
        component: VistoriaDetailComponent,
        resolve: {
            vistoria: VistoriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vistorias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vistoria/new',
        component: VistoriaUpdateComponent,
        resolve: {
            vistoria: VistoriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vistorias'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vistoria/:id/edit',
        component: VistoriaUpdateComponent,
        resolve: {
            vistoria: VistoriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vistorias'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vistoriaPopupRoute: Routes = [
    {
        path: 'vistoria/:id/delete',
        component: VistoriaDeletePopupComponent,
        resolve: {
            vistoria: VistoriaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Vistorias'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
