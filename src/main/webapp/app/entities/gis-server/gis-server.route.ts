import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { GisServerComponent } from './gis-server.component';
import { GisServerDetailComponent } from './gis-server-detail.component';
import { GisServerPopupComponent } from './gis-server-dialog.component';
import { GisServerDeletePopupComponent } from './gis-server-delete-dialog.component';

@Injectable()
export class GisServerResolvePagingParams implements Resolve<any> {

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

export const gisServerRoute: Routes = [
    {
        path: 'gis-server',
        component: GisServerComponent,
        resolve: {
            'pagingParams': GisServerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GisServers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gis-server/:id',
        component: GisServerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GisServers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gisServerPopupRoute: Routes = [
    {
        path: 'gis-server-new',
        component: GisServerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GisServers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gis-server/:id/edit',
        component: GisServerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GisServers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gis-server/:id/delete',
        component: GisServerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GisServers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
