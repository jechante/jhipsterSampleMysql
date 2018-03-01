import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleMysqlSharedModule } from '../../shared';
import {
    GisServerService,
    GisServerPopupService,
    GisServerComponent,
    GisServerDetailComponent,
    GisServerDialogComponent,
    GisServerPopupComponent,
    GisServerDeletePopupComponent,
    GisServerDeleteDialogComponent,
    gisServerRoute,
    gisServerPopupRoute,
    GisServerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...gisServerRoute,
    ...gisServerPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleMysqlSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GisServerComponent,
        GisServerDetailComponent,
        GisServerDialogComponent,
        GisServerDeleteDialogComponent,
        GisServerPopupComponent,
        GisServerDeletePopupComponent,
    ],
    entryComponents: [
        GisServerComponent,
        GisServerDialogComponent,
        GisServerPopupComponent,
        GisServerDeleteDialogComponent,
        GisServerDeletePopupComponent,
    ],
    providers: [
        GisServerService,
        GisServerPopupService,
        GisServerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleMysqlGisServerModule {}
