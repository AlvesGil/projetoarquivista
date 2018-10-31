import { IPagamento } from 'app/shared/model//pagamento.model';
import { IImovel } from 'app/shared/model//imovel.model';
import { ILogin } from 'app/shared/model//login.model';
import { IArquivista } from 'app/shared/model//arquivista.model';

export interface ILocador {
    id?: number;
    imovel?: string;
    nome?: string;
    end?: string;
    idt?: string;
    cpf?: string;
    pagamentos?: IPagamento[];
    imovel?: IImovel;
    login?: ILogin;
    arquivistas?: IArquivista[];
}

export class Locador implements ILocador {
    constructor(
        public id?: number,
        public imovel?: string,
        public nome?: string,
        public end?: string,
        public idt?: string,
        public cpf?: string,
        public pagamentos?: IPagamento[],
        public imovel?: IImovel,
        public login?: ILogin,
        public arquivistas?: IArquivista[]
    ) {}
}
