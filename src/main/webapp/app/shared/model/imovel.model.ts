import { IArquivista } from 'app/shared/model//arquivista.model';

export interface IImovel {
    id?: number;
    nome?: string;
    end?: string;
    contrato?: string;
    ficha?: string;
    oficios?: string;
    aditivo?: string;
    arquivistas?: IArquivista[];
}

export class Imovel implements IImovel {
    constructor(
        public id?: number,
        public nome?: string,
        public end?: string,
        public contrato?: string,
        public ficha?: string,
        public oficios?: string,
        public aditivo?: string,
        public arquivistas?: IArquivista[]
    ) {}
}
