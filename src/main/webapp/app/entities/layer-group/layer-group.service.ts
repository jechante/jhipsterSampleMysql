import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { LayerGroup } from './layer-group.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LayerGroup>;

@Injectable()
export class LayerGroupService {

    private resourceUrl =  SERVER_API_URL + 'api/layer-groups';

    constructor(private http: HttpClient) { }

    create(layerGroup: LayerGroup): Observable<EntityResponseType> {
        const copy = this.convert(layerGroup);
        return this.http.post<LayerGroup>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(layerGroup: LayerGroup): Observable<EntityResponseType> {
        const copy = this.convert(layerGroup);
        return this.http.put<LayerGroup>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LayerGroup>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LayerGroup[]>> {
        const options = createRequestOption(req);
        return this.http.get<LayerGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LayerGroup[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LayerGroup = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LayerGroup[]>): HttpResponse<LayerGroup[]> {
        const jsonResponse: LayerGroup[] = res.body;
        const body: LayerGroup[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LayerGroup.
     */
    private convertItemFromServer(layerGroup: LayerGroup): LayerGroup {
        const copy: LayerGroup = Object.assign({}, layerGroup);
        return copy;
    }

    /**
     * Convert a LayerGroup to a JSON which can be sent to the server.
     */
    private convert(layerGroup: LayerGroup): LayerGroup {
        const copy: LayerGroup = Object.assign({}, layerGroup);
        return copy;
    }
}
