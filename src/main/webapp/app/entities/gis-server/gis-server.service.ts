import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { GisServer } from './gis-server.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<GisServer>;

@Injectable()
export class GisServerService {

    private resourceUrl =  SERVER_API_URL + 'api/gis-servers';

    constructor(private http: HttpClient) { }

    create(gisServer: GisServer): Observable<EntityResponseType> {
        const copy = this.convert(gisServer);
        return this.http.post<GisServer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(gisServer: GisServer): Observable<EntityResponseType> {
        const copy = this.convert(gisServer);
        return this.http.put<GisServer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<GisServer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<GisServer[]>> {
        const options = createRequestOption(req);
        return this.http.get<GisServer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<GisServer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: GisServer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<GisServer[]>): HttpResponse<GisServer[]> {
        const jsonResponse: GisServer[] = res.body;
        const body: GisServer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to GisServer.
     */
    private convertItemFromServer(gisServer: GisServer): GisServer {
        const copy: GisServer = Object.assign({}, gisServer);
        return copy;
    }

    /**
     * Convert a GisServer to a JSON which can be sent to the server.
     */
    private convert(gisServer: GisServer): GisServer {
        const copy: GisServer = Object.assign({}, gisServer);
        return copy;
    }
}
