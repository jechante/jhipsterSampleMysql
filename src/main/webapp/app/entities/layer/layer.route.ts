import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { LayerComponent } from './layer.component';
import { LayerDetailComponent } from './layer-detail.component';
import { LayerPopupComponent } from './layer-dialog.component';
import { LayerDeletePopupComponent } from './layer-delete-dialog.component';

@Injectable()
export class LayerResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const layerRoute: Routes = [
    {
        path: 'layer',
        component: LayerComponent,
        resolve: {
            'pagingParams': LayerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Layers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'layer/:id',
        component: LayerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Layers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const layerPopupRoute: Routes = [
    {
        path: 'layer-new',
        component: LayerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Layers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'layer/:id/edit',
        component: LayerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Layers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'layer/:id/delete',
        component: LayerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Layers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
