import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { LayerGroupComponent } from './layer-group.component';
import { LayerGroupDetailComponent } from './layer-group-detail.component';
import { LayerGroupPopupComponent } from './layer-group-dialog.component';
import { LayerGroupDeletePopupComponent } from './layer-group-delete-dialog.component';

@Injectable()
export class LayerGroupResolvePagingParams implements Resolve<any> {

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

export const layerGroupRoute: Routes = [
    {
        path: 'layer-group',
        component: LayerGroupComponent,
        resolve: {
            'pagingParams': LayerGroupResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LayerGroups'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'layer-group/:id',
        component: LayerGroupDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LayerGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const layerGroupPopupRoute: Routes = [
    {
        path: 'layer-group-new',
        component: LayerGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LayerGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'layer-group/:id/edit',
        component: LayerGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LayerGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'layer-group/:id/delete',
        component: LayerGroupDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LayerGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
