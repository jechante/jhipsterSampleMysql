import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleMysqlLayerModule } from './layer/layer.module';
import { JhipsterSampleMysqlLayerGroupModule } from './layer-group/layer-group.module';
import { JhipsterSampleMysqlLegendModule } from './legend/legend.module';
import { JhipsterSampleMysqlGisServerModule } from './gis-server/gis-server.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleMysqlLayerModule,
        JhipsterSampleMysqlLayerGroupModule,
        JhipsterSampleMysqlLegendModule,
        JhipsterSampleMysqlGisServerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleMysqlEntityModule {}
