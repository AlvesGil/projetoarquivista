import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArquivistaSharedModule } from 'app/shared';
import {
    ImovelComponent,
    ImovelDetailComponent,
    ImovelUpdateComponent,
    ImovelDeletePopupComponent,
    ImovelDeleteDialogComponent,
    imovelRoute,
    imovelPopupRoute
} from './';

const ENTITY_STATES = [...imovelRoute, ...imovelPopupRoute];

@NgModule({
    imports: [ArquivistaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ImovelComponent, ImovelDetailComponent, ImovelUpdateComponent, ImovelDeleteDialogComponent, ImovelDeletePopupComponent],
    entryComponents: [ImovelComponent, ImovelUpdateComponent, ImovelDeleteDialogComponent, ImovelDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArquivistaImovelModule {}
