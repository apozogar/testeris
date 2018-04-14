import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Paso } from './paso.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Paso>;

@Injectable()
export class PasoService {

    private resourceUrl =  SERVER_API_URL + 'api/pasos';

    constructor(private http: HttpClient) { }

    create(paso: Paso): Observable<EntityResponseType> {
        const copy = this.convert(paso);
        return this.http.post<Paso>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(paso: Paso): Observable<EntityResponseType> {
        const copy = this.convert(paso);
        return this.http.put<Paso>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Paso>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Paso[]>> {
        const options = createRequestOption(req);
        return this.http.get<Paso[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Paso[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Paso = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Paso[]>): HttpResponse<Paso[]> {
        const jsonResponse: Paso[] = res.body;
        const body: Paso[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Paso.
     */
    private convertItemFromServer(paso: Paso): Paso {
        const copy: Paso = Object.assign({}, paso);
        return copy;
    }

    /**
     * Convert a Paso to a JSON which can be sent to the server.
     */
    private convert(paso: Paso): Paso {
        const copy: Paso = Object.assign({}, paso);
        return copy;
    }
}
