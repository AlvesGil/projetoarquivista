import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IArquivista } from 'app/shared/model/arquivista.model';

type EntityResponseType = HttpResponse<IArquivista>;
type EntityArrayResponseType = HttpResponse<IArquivista[]>;

@Injectable({ providedIn: 'root' })
export class ArquivistaService {
    public resourceUrl = SERVER_API_URL + 'api/arquivistas';

    constructor(private http: HttpClient) {}

    create(arquivista: IArquivista): Observable<EntityResponseType> {
        return this.http.post<IArquivista>(this.resourceUrl, arquivista, { observe: 'response' });
    }

    update(arquivista: IArquivista): Observable<EntityResponseType> {
        return this.http.put<IArquivista>(this.resourceUrl, arquivista, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IArquivista>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IArquivista[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
