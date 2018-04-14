import { BaseEntity } from './../../shared';

export const enum Estado {
    'OK',
    'KO',
    'NA'
}

export class Caso implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public estado?: Estado,
        public proyecto?: BaseEntity,
        public pasos?: BaseEntity[],
    ) {
    }
}
