import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Caso } from './caso.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Caso>;

@Injectable()
export class CasoService {

    private resourceUrl =  SERVER_API_URL + 'api/casos';

    constructor(private http: HttpClient) { }

    create(caso: Caso): Observable<EntityResponseType> {
        const copy = this.convert(caso);
        return this.http.post<Caso>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(caso: Caso): Observable<EntityResponseType> {
        const copy = this.convert(caso);
        return this.http.put<Caso>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Caso>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Caso[]>> {
        const options = createRequestOption(req);
        return this.http.get<Caso[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Caso[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Caso = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Caso[]>): HttpResponse<Caso[]> {
        const jsonResponse: Caso[] = res.body;
        const body: Caso[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Caso.
     */
    private convertItemFromServer(caso: Caso): Caso {
        const copy: Caso = Object.assign({}, caso);
        return copy;
    }

    /**
     * Convert a Caso to a JSON which can be sent to the server.
     */
    private convert(caso: Caso): Caso {
        const copy: Caso = Object.assign({}, caso);
        return copy;
    }
}
