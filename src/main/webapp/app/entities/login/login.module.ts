import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArquivistaSharedModule } from 'app/shared';
import {
    LoginComponent,
    LoginDetailComponent,
    LoginUpdateComponent,
    LoginDeletePopupComponent,
    LoginDeleteDialogComponent,
    loginRoute,
    loginPopupRoute
} from './';

const ENTITY_STATES = [...loginRoute, ...loginPopupRoute];

@NgModule({
    imports: [ArquivistaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LoginComponent, LoginDetailComponent, LoginUpdateComponent, LoginDeleteDialogComponent, LoginDeletePopupComponent],
    entryComponents: [LoginComponent, LoginUpdateComponent, LoginDeleteDialogComponent, LoginDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArquivistaLoginModule {}
