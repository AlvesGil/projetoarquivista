import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArquivistaSharedModule } from 'app/shared';
import {
    ArquivistaComponent,
    ArquivistaDetailComponent,
    ArquivistaUpdateComponent,
    ArquivistaDeletePopupComponent,
    ArquivistaDeleteDialogComponent,
    arquivistaRoute,
    arquivistaPopupRoute
} from './';

const ENTITY_STATES = [...arquivistaRoute, ...arquivistaPopupRoute];

@NgModule({
    imports: [ArquivistaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ArquivistaComponent,
        ArquivistaDetailComponent,
        ArquivistaUpdateComponent,
        ArquivistaDeleteDialogComponent,
        ArquivistaDeletePopupComponent
    ],
    entryComponents: [ArquivistaComponent, ArquivistaUpdateComponent, ArquivistaDeleteDialogComponent, ArquivistaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArquivistaArquivistaModule {}
