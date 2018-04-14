import { BaseEntity } from './../../shared';

export const enum Estado {
    'OK',
    'KO',
    'NA'
}

export class Paso implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public numPaso?: string,
        public resultadoEsperado?: string,
        public estado?: Estado,
        public caso?: BaseEntity,
        public imagens?: BaseEntity[],
    ) {
    }
}
