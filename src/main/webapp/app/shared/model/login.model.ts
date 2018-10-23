import { IArquivista } from 'app/shared/model//arquivista.model';
import { ILocador } from 'app/shared/model//locador.model';

export interface ILogin {
    id?: number;
    usuario?: string;
    senha?: number;
    arquivista?: IArquivista;
    locador?: ILocador;
}

export class Login implements ILogin {
    constructor(
        public id?: number,
        public usuario?: string,
        public senha?: number,
        public arquivista?: IArquivista,
        public locador?: ILocador
    ) {}
}
