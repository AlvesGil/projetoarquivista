import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArquivistaSharedModule } from 'app/shared';
import {
    VistoriaComponent,
    VistoriaDetailComponent,
    VistoriaUpdateComponent,
    VistoriaDeletePopupComponent,
    VistoriaDeleteDialogComponent,
    vistoriaRoute,
    vistoriaPopupRoute
} from './';

const ENTITY_STATES = [...vistoriaRoute, ...vistoriaPopupRoute];

@NgModule({
    imports: [ArquivistaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VistoriaComponent,
        VistoriaDetailComponent,
        VistoriaUpdateComponent,
        VistoriaDeleteDialogComponent,
        VistoriaDeletePopupComponent
    ],
    entryComponents: [VistoriaComponent, VistoriaUpdateComponent, VistoriaDeleteDialogComponent, VistoriaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArquivistaVistoriaModule {}
