import { IImovel } from 'app/shared/model//imovel.model';
import { IArquivista } from 'app/shared/model//arquivista.model';

export interface ILocatario {
    id?: number;
    imovel?: string;
    nome?: string;
    end?: string;
    idt?: string;
    cpf?: string;
    imovel?: IImovel;
    arquivistas?: IArquivista[];
}

export class Locatario implements ILocatario {
    constructor(
        public id?: number,
        public imovel?: string,
        public nome?: string,
        public end?: string,
        public idt?: string,
        public cpf?: string,
        public imovel?: IImovel,
        public arquivistas?: IArquivista[]
    ) {}
}
