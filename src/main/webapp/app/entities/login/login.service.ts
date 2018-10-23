import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILogin } from 'app/shared/model/login.model';

type EntityResponseType = HttpResponse<ILogin>;
type EntityArrayResponseType = HttpResponse<ILogin[]>;

@Injectable({ providedIn: 'root' })
export class LoginService {
    public resourceUrl = SERVER_API_URL + 'api/logins';

    constructor(private http: HttpClient) {}

    create(login: ILogin): Observable<EntityResponseType> {
        return this.http.post<ILogin>(this.resourceUrl, login, { observe: 'response' });
    }

    update(login: ILogin): Observable<EntityResponseType> {
        return this.http.put<ILogin>(this.resourceUrl, login, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILogin>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILogin[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
