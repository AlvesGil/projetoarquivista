import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArquivistaSharedModule } from 'app/shared';
import {
    LocadorComponent,
    LocadorDetailComponent,
    LocadorUpdateComponent,
    LocadorDeletePopupComponent,
    LocadorDeleteDialogComponent,
    locadorRoute,
    locadorPopupRoute
} from './';

const ENTITY_STATES = [...locadorRoute, ...locadorPopupRoute];

@NgModule({
    imports: [ArquivistaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LocadorComponent,
        LocadorDetailComponent,
        LocadorUpdateComponent,
        LocadorDeleteDialogComponent,
        LocadorDeletePopupComponent
    ],
    entryComponents: [LocadorComponent, LocadorUpdateComponent, LocadorDeleteDialogComponent, LocadorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArquivistaLocadorModule {}
