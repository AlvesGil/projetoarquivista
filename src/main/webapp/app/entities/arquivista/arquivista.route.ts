import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Arquivista } from 'app/shared/model/arquivista.model';
import { ArquivistaService } from './arquivista.service';
import { ArquivistaComponent } from './arquivista.component';
import { ArquivistaDetailComponent } from './arquivista-detail.component';
import { ArquivistaUpdateComponent } from './arquivista-update.component';
import { ArquivistaDeletePopupComponent } from './arquivista-delete-dialog.component';
import { IArquivista } from 'app/shared/model/arquivista.model';

@Injectable({ providedIn: 'root' })
export class ArquivistaResolve implements Resolve<IArquivista> {
    constructor(private service: ArquivistaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((arquivista: HttpResponse<Arquivista>) => arquivista.body));
        }
        return of(new Arquivista());
    }
}

export const arquivistaRoute: Routes = [
    {
        path: 'arquivista',
        component: ArquivistaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Arquivistas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'arquivista/:id/view',
        component: ArquivistaDetailComponent,
        resolve: {
            arquivista: ArquivistaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Arquivistas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'arquivista/new',
        component: ArquivistaUpdateComponent,
        resolve: {
            arquivista: ArquivistaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Arquivistas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'arquivista/:id/edit',
        component: ArquivistaUpdateComponent,
        resolve: {
            arquivista: ArquivistaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Arquivistas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const arquivistaPopupRoute: Routes = [
    {
        path: 'arquivista/:id/delete',
        component: ArquivistaDeletePopupComponent,
        resolve: {
            arquivista: ArquivistaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Arquivistas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
