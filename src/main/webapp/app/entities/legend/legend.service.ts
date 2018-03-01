import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Legend } from './legend.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Legend>;

@Injectable()
export class LegendService {

    private resourceUrl =  SERVER_API_URL + 'api/legends';

    constructor(private http: HttpClient) { }

    create(legend: Legend): Observable<EntityResponseType> {
        const copy = this.convert(legend);
        return this.http.post<Legend>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(legend: Legend): Observable<EntityResponseType> {
        const copy = this.convert(legend);
        return this.http.put<Legend>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Legend>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Legend[]>> {
        const options = createRequestOption(req);
        return this.http.get<Legend[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Legend[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Legend = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Legend[]>): HttpResponse<Legend[]> {
        const jsonResponse: Legend[] = res.body;
        const body: Legend[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Legend.
     */
    private convertItemFromServer(legend: Legend): Legend {
        const copy: Legend = Object.assign({}, legend);
        return copy;
    }

    /**
     * Convert a Legend to a JSON which can be sent to the server.
     */
    private convert(legend: Legend): Legend {
        const copy: Legend = Object.assign({}, legend);
        return copy;
    }
}
