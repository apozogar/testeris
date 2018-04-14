import { BaseEntity } from './../../shared';

export class Imagen implements BaseEntity {
    constructor(
        public id?: number,
        public imagenContentType?: string,
        public imagen?: any,
        public nombre?: string,
        public description?: string,
        public paso?: BaseEntity,
    ) {
    }
}
