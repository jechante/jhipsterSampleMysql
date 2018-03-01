import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleMysqlSharedModule } from '../../shared';
import {
    LayerGroupService,
    LayerGroupPopupService,
    LayerGroupComponent,
    LayerGroupDetailComponent,
    LayerGroupDialogComponent,
    LayerGroupPopupComponent,
    LayerGroupDeletePopupComponent,
    LayerGroupDeleteDialogComponent,
    layerGroupRoute,
    layerGroupPopupRoute,
    LayerGroupResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...layerGroupRoute,
    ...layerGroupPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleMysqlSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LayerGroupComponent,
        LayerGroupDetailComponent,
        LayerGroupDialogComponent,
        LayerGroupDeleteDialogComponent,
        LayerGroupPopupComponent,
        LayerGroupDeletePopupComponent,
    ],
    entryComponents: [
        LayerGroupComponent,
        LayerGroupDialogComponent,
        LayerGroupPopupComponent,
        LayerGroupDeleteDialogComponent,
        LayerGroupDeletePopupComponent,
    ],
    providers: [
        LayerGroupService,
        LayerGroupPopupService,
        LayerGroupResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleMysqlLayerGroupModule {}
