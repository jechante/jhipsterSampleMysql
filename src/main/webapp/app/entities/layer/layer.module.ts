import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleMysqlSharedModule } from '../../shared';
import {
    LayerService,
    LayerPopupService,
    LayerComponent,
    LayerDetailComponent,
    LayerDialogComponent,
    LayerPopupComponent,
    LayerDeletePopupComponent,
    LayerDeleteDialogComponent,
    layerRoute,
    layerPopupRoute,
    LayerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...layerRoute,
    ...layerPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleMysqlSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LayerComponent,
        LayerDetailComponent,
        LayerDialogComponent,
        LayerDeleteDialogComponent,
        LayerPopupComponent,
        LayerDeletePopupComponent,
    ],
    entryComponents: [
        LayerComponent,
        LayerDialogComponent,
        LayerPopupComponent,
        LayerDeleteDialogComponent,
        LayerDeletePopupComponent,
    ],
    providers: [
        LayerService,
        LayerPopupService,
        LayerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleMysqlLayerModule {}
