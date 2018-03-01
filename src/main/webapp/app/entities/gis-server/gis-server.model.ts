import { BaseEntity } from './../../shared';

export const enum ServerVendor {
    'GEOSERVER',
    'ARCGIS',
    'SUPERMAP'
}

export class GisServer implements BaseEntity {
    constructor(
        public id?: number,
        public serverName?: string,
        public url?: string,
        public vendor?: ServerVendor,
        public layers?: BaseEntity[],
    ) {
    }
}
