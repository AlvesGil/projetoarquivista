import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Imovel } from 'app/shared/model/imovel.model';
import { ImovelService } from './imovel.service';
import { ImovelComponent } from './imovel.component';
import { ImovelDetailComponent } from './imovel-detail.component';
import { ImovelUpdateComponent } from './imovel-update.component';
import { ImovelDeletePopupComponent } from './imovel-delete-dialog.component';
import { IImovel } from 'app/shared/model/imovel.model';

@Injectable({ providedIn: 'root' })
export class ImovelResolve implements Resolve<IImovel> {
    constructor(private service: ImovelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((imovel: HttpResponse<Imovel>) => imovel.body));
        }
        return of(new Imovel());
    }
}

export const imovelRoute: Routes = [
    {
        path: 'imovel',
        component: ImovelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Imovels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'imovel/:id/view',
        component: ImovelDetailComponent,
        resolve: {
            imovel: ImovelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Imovels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'imovel/new',
        component: ImovelUpdateComponent,
        resolve: {
            imovel: ImovelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Imovels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'imovel/:id/edit',
        component: ImovelUpdateComponent,
        resolve: {
            imovel: ImovelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Imovels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imovelPopupRoute: Routes = [
    {
        path: 'imovel/:id/delete',
        component: ImovelDeletePopupComponent,
        resolve: {
            imovel: ImovelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Imovels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
