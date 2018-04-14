import { BaseEntity } from './../../shared';

export class Proyecto implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public url?: string,
        public casos?: BaseEntity[],
    ) {
    }
}
