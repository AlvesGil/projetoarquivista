import { IVistoria } from 'app/shared/model//vistoria.model';
import { ILocatario } from 'app/shared/model//locatario.model';
import { ILocador } from 'app/shared/model//locador.model';
import { IArquivista } from 'app/shared/model//arquivista.model';

export interface IImovel {
    id?: number;
    nome?: string;
    end?: string;
    contrato?: string;
    ficha?: string;
    oficios?: string;
    aditivo?: string;
    vistoria?: IVistoria;
    locatarios?: ILocatario[];
    locadors?: ILocador[];
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
        public vistoria?: IVistoria,
        public locatarios?: ILocatario[],
        public locadors?: ILocador[],
        public arquivistas?: IArquivista[]
    ) {}
}
