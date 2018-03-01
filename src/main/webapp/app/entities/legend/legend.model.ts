import { BaseEntity } from './../../shared';

export class Legend implements BaseEntity {
    constructor(
        public id?: number,
        public legendName?: string,
        public logo?: string,
        public layer?: BaseEntity,
    ) {
    }
}
