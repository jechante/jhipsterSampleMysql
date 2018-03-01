import { BaseEntity } from './../../shared';

export class LayerGroup implements BaseEntity {
    constructor(
        public id?: number,
        public groupName?: string,
        public layers?: BaseEntity[],
        public subGroups?: BaseEntity[],
        public parentGroup?: BaseEntity,
    ) {
    }
}
