import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { LegendComponent } from './legend.component';
import { LegendDetailComponent } from './legend-detail.component';
import { LegendPopupComponent } from './legend-dialog.component';
import { LegendDeletePopupComponent } from './legend-delete-dialog.component';

@Injectable()
export class LegendResolvePagingParams implements Resolve<any> {

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

export const legendRoute: Routes = [
    {
        path: 'legend',
        component: LegendComponent,
        resolve: {
            'pagingParams': LegendResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Legends'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'legend/:id',
        component: LegendDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Legends'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const legendPopupRoute: Routes = [
    {
        path: 'legend-new',
        component: LegendPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Legends'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'legend/:id/edit',
        component: LegendPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Legends'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'legend/:id/delete',
        component: LegendDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Legends'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
