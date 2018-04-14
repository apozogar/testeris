import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Proyecto } from './proyecto.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Proyecto>;

@Injectable()
export class ProyectoService {

    private resourceUrl =  SERVER_API_URL + 'api/proyectos';

    constructor(private http: HttpClient) { }

    create(proyecto: Proyecto): Observable<EntityResponseType> {
        const copy = this.convert(proyecto);
        return this.http.post<Proyecto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(proyecto: Proyecto): Observable<EntityResponseType> {
        const copy = this.convert(proyecto);
        return this.http.put<Proyecto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Proyecto>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Proyecto[]>> {
        const options = createRequestOption(req);
        return this.http.get<Proyecto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Proyecto[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Proyecto = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Proyecto[]>): HttpResponse<Proyecto[]> {
        const jsonResponse: Proyecto[] = res.body;
        const body: Proyecto[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Proyecto.
     */
    private convertItemFromServer(proyecto: Proyecto): Proyecto {
        const copy: Proyecto = Object.assign({}, proyecto);
        return copy;
    }

    /**
     * Convert a Proyecto to a JSON which can be sent to the server.
     */
    private convert(proyecto: Proyecto): Proyecto {
        const copy: Proyecto = Object.assign({}, proyecto);
        return copy;
    }
}
