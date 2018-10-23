import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocatario } from 'app/shared/model/locatario.model';

type EntityResponseType = HttpResponse<ILocatario>;
type EntityArrayResponseType = HttpResponse<ILocatario[]>;

@Injectable({ providedIn: 'root' })
export class LocatarioService {
    public resourceUrl = SERVER_API_URL + 'api/locatarios';

    constructor(private http: HttpClient) {}

    create(locatario: ILocatario): Observable<EntityResponseType> {
        return this.http.post<ILocatario>(this.resourceUrl, locatario, { observe: 'response' });
    }

    update(locatario: ILocatario): Observable<EntityResponseType> {
        return this.http.put<ILocatario>(this.resourceUrl, locatario, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILocatario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILocatario[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
