import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArquivistaSharedModule } from 'app/shared';
import {
    ComprovanteComponent,
    ComprovanteDetailComponent,
    ComprovanteUpdateComponent,
    ComprovanteDeletePopupComponent,
    ComprovanteDeleteDialogComponent,
    comprovanteRoute,
    comprovantePopupRoute
} from './';

const ENTITY_STATES = [...comprovanteRoute, ...comprovantePopupRoute];

@NgModule({
    imports: [ArquivistaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ComprovanteComponent,
        ComprovanteDetailComponent,
        ComprovanteUpdateComponent,
        ComprovanteDeleteDialogComponent,
        ComprovanteDeletePopupComponent
    ],
    entryComponents: [ComprovanteComponent, ComprovanteUpdateComponent, ComprovanteDeleteDialogComponent, ComprovanteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArquivistaComprovanteModule {}
