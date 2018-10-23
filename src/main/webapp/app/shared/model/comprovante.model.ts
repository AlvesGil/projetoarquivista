import { IArquivista } from 'app/shared/model//arquivista.model';

export interface IComprovante {
    id?: number;
    mes?: string;
    ano?: number;
    arquivistas?: IArquivista[];
}

export class Comprovante implements IComprovante {
    constructor(public id?: number, public mes?: string, public ano?: number, public arquivistas?: IArquivista[]) {}
}
