import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TesterisProyectoModule } from './proyecto/proyecto.module';
import { TesterisCasoModule } from './caso/caso.module';
import { TesterisPasoModule } from './paso/paso.module';
import { TesterisImagenModule } from './imagen/imagen.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TesterisProyectoModule,
        TesterisCasoModule,
        TesterisPasoModule,
        TesterisImagenModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesterisEntityModule {}
