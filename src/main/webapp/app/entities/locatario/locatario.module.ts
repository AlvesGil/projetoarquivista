import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArquivistaSharedModule } from 'app/shared';
import {
    LocatarioComponent,
    LocatarioDetailComponent,
    LocatarioUpdateComponent,
    LocatarioDeletePopupComponent,
    LocatarioDeleteDialogComponent,
    locatarioRoute,
    locatarioPopupRoute
} from './';

const ENTITY_STATES = [...locatarioRoute, ...locatarioPopupRoute];

@NgModule({
    imports: [ArquivistaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LocatarioComponent,
        LocatarioDetailComponent,
        LocatarioUpdateComponent,
        LocatarioDeleteDialogComponent,
        LocatarioDeletePopupComponent
    ],
    entryComponents: [LocatarioComponent, LocatarioUpdateComponent, LocatarioDeleteDialogComponent, LocatarioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArquivistaLocatarioModule {}
