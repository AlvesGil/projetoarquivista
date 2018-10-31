import { IImovel } from 'app/shared/model//imovel.model';
import { IArquivista } from 'app/shared/model//arquivista.model';

export interface IVistoria {
    id?: number;
    documentos?: string;
    imagens?: string;
    imovels?: IImovel[];
    arquivistas?: IArquivista[];
}

export class Vistoria implements IVistoria {
    constructor(
        public id?: number,
        public documentos?: string,
        public imagens?: string,
        public imovels?: IImovel[],
        public arquivistas?: IArquivista[]
    ) {}
}
