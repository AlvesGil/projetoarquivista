import { ILocador } from 'app/shared/model//locador.model';
import { IArquivista } from 'app/shared/model//arquivista.model';

export interface IPagamento {
    id?: number;
    condominio?: string;
    iptu?: string;
    luz?: string;
    agua?: string;
    diversos?: string;
    locador?: ILocador;
    arquivistas?: IArquivista[];
}

export class Pagamento implements IPagamento {
    constructor(
        public id?: number,
        public condominio?: string,
        public iptu?: string,
        public luz?: string,
        public agua?: string,
        public diversos?: string,
        public locador?: ILocador,
        public arquivistas?: IArquivista[]
    ) {}
}
