import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Layer } from './layer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Layer>;

@Injectable()
export class LayerService {

    private resourceUrl =  SERVER_API_URL + 'api/layers';

    constructor(private http: HttpClient) { }

    create(layer: Layer): Observable<EntityResponseType> {
        const copy = this.convert(layer);
        return this.http.post<Layer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(layer: Layer): Observable<EntityResponseType> {
        const copy = this.convert(layer);
        return this.http.put<Layer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Layer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Layer[]>> {
        const options = createRequestOption(req);
        return this.http.get<Layer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Layer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Layer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Layer[]>): HttpResponse<Layer[]> {
        const jsonResponse: Layer[] = res.body;
        const body: Layer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Layer.
     */
    private convertItemFromServer(layer: Layer): Layer {
        const copy: Layer = Object.assign({}, layer);
        return copy;
    }

    /**
     * Convert a Layer to a JSON which can be sent to the server.
     */
    private convert(layer: Layer): Layer {
        const copy: Layer = Object.assign({}, layer);
        return copy;
    }
}
