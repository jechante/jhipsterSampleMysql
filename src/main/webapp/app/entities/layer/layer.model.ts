import { BaseEntity } from './../../shared';

export const enum ServerType {
    'OGC_WMS',
    'OGC_WFS',
    'OGC_WMST',
    'OGC_WCS',
    'ARCGIS_REST',
    'SUPERMAP_REST'
}

export const enum PointQueryType {
    'WMS_GETFEATUREINFO',
    'WFS_CQL',
    'WFS_OGC',
    'ARCGIS_REST',
    'SUPERMAP_REST'
}

export const enum PoiType {
    'XY',
    'WFS',
    'ARCGIS_REST',
    'SUPERMAP_REST'
}

export class Layer implements BaseEntity {
    constructor(
        public id?: number,
        public layerName?: string,
        public identifier?: string,
        public source?: ServerType,
        public pointQueryType?: PointQueryType,
        public style?: string,
        public poiType?: PoiType,
        public poiURL?: string,
        public legends?: BaseEntity[],
        public gisServer?: BaseEntity,
        public layerGroup?: BaseEntity,
    ) {
    }
}
