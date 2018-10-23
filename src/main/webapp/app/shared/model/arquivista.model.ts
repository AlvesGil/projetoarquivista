import { ILocador } from 'app/shared/model//locador.model';
import { ILocatario } from 'app/shared/model//locatario.model';
import { IImovel } from 'app/shared/model//imovel.model';
import { IPagamento } from 'app/shared/model//pagamento.model';
import { IComprovante } from 'app/shared/model//comprovante.model';
import { IVistoria } from 'app/shared/model//vistoria.model';
import { ILogin } from 'app/shared/model//login.model';

export interface IArquivista {
    id?: number;
    cadlocador?: string;
    cadlocatario?: string;
    cadimovel?: string;
    locadors?: ILocador[];
    locatarios?: ILocatario[];
    imovels?: IImovel[];
    pagamentos?: IPagamento[];
    comprovantes?: IComprovante[];
    vistorias?: IVistoria[];
    login?: ILogin;
}

export class Arquivista implements IArquivista {
    constructor(
        public id?: number,
        public cadlocador?: string,
        public cadlocatario?: string,
        public cadimovel?: string,
        public locadors?: ILocador[],
        public locatarios?: ILocatario[],
        public imovels?: IImovel[],
        public pagamentos?: IPagamento[],
        public comprovantes?: IComprovante[],
        public vistorias?: IVistoria[],
        public login?: ILogin
    ) {}
}
