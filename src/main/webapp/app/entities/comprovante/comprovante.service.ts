import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IComprovante } from 'app/shared/model/comprovante.model';

type EntityResponseType = HttpResponse<IComprovante>;
type EntityArrayResponseType = HttpResponse<IComprovante[]>;

@Injectable({ providedIn: 'root' })
export class ComprovanteService {
    public resourceUrl = SERVER_API_URL + 'api/comprovantes';

    constructor(private http: HttpClient) {}

    create(comprovante: IComprovante): Observable<EntityResponseType> {
        return this.http.post<IComprovante>(this.resourceUrl, comprovante, { observe: 'response' });
    }

    update(comprovante: IComprovante): Observable<EntityResponseType> {
        return this.http.put<IComprovante>(this.resourceUrl, comprovante, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IComprovante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IComprovante[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
