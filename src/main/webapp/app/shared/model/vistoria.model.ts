import { IArquivista } from 'app/shared/model//arquivista.model';

export interface IVistoria {
    id?: number;
    documentos?: string;
    imagens?: string;
    arquivistas?: IArquivista[];
}

export class Vistoria implements IVistoria {
    constructor(public id?: number, public documentos?: string, public imagens?: string, public arquivistas?: IArquivista[]) {}
}
