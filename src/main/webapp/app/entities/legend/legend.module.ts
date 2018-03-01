import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleMysqlSharedModule } from '../../shared';
import {
    LegendService,
    LegendPopupService,
    LegendComponent,
    LegendDetailComponent,
    LegendDialogComponent,
    LegendPopupComponent,
    LegendDeletePopupComponent,
    LegendDeleteDialogComponent,
    legendRoute,
    legendPopupRoute,
    LegendResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...legendRoute,
    ...legendPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleMysqlSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LegendComponent,
        LegendDetailComponent,
        LegendDialogComponent,
        LegendDeleteDialogComponent,
        LegendPopupComponent,
        LegendDeletePopupComponent,
    ],
    entryComponents: [
        LegendComponent,
        LegendDialogComponent,
        LegendPopupComponent,
        LegendDeleteDialogComponent,
        LegendDeletePopupComponent,
    ],
    providers: [
        LegendService,
        LegendPopupService,
        LegendResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleMysqlLegendModule {}
