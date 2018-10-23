import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVistoria } from 'app/shared/model/vistoria.model';

type EntityResponseType = HttpResponse<IVistoria>;
type EntityArrayResponseType = HttpResponse<IVistoria[]>;

@Injectable({ providedIn: 'root' })
export class VistoriaService {
    public resourceUrl = SERVER_API_URL + 'api/vistorias';

    constructor(private http: HttpClient) {}

    create(vistoria: IVistoria): Observable<EntityResponseType> {
        return this.http.post<IVistoria>(this.resourceUrl, vistoria, { observe: 'response' });
    }

    update(vistoria: IVistoria): Observable<EntityResponseType> {
        return this.http.put<IVistoria>(this.resourceUrl, vistoria, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVistoria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVistoria[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
