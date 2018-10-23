import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ArquivistaLoginModule } from './login/login.module';
import { ArquivistaArquivistaModule } from './arquivista/arquivista.module';
import { ArquivistaLocadorModule } from './locador/locador.module';
import { ArquivistaLocatarioModule } from './locatario/locatario.module';
import { ArquivistaImovelModule } from './imovel/imovel.module';
import { ArquivistaPagamentoModule } from './pagamento/pagamento.module';
import { ArquivistaComprovanteModule } from './comprovante/comprovante.module';
import { ArquivistaVistoriaModule } from './vistoria/vistoria.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ArquivistaLoginModule,
        ArquivistaArquivistaModule,
        ArquivistaLocadorModule,
        ArquivistaLocatarioModule,
        ArquivistaImovelModule,
        ArquivistaPagamentoModule,
        ArquivistaComprovanteModule,
        ArquivistaVistoriaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArquivistaEntityModule {}
