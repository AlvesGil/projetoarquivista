import { NgModule } from '@angular/core';

import { ArquivistaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ArquivistaSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ArquivistaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ArquivistaSharedCommonModule {}
