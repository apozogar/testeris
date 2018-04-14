import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TesterisSharedModule } from '../../shared';
import {
    ProyectoService,
    ProyectoPopupService,
    ProyectoComponent,
    ProyectoDetailComponent,
    ProyectoDialogComponent,
    ProyectoPopupComponent,
    ProyectoDeletePopupComponent,
    ProyectoDeleteDialogComponent,
    proyectoRoute,
    proyectoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...proyectoRoute,
    ...proyectoPopupRoute,
];

@NgModule({
    imports: [
        TesterisSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProyectoComponent,
        ProyectoDetailComponent,
        ProyectoDialogComponent,
        ProyectoDeleteDialogComponent,
        ProyectoPopupComponent,
        ProyectoDeletePopupComponent,
    ],
    entryComponents: [
        ProyectoComponent,
        ProyectoDialogComponent,
        ProyectoPopupComponent,
        ProyectoDeleteDialogComponent,
        ProyectoDeletePopupComponent,
    ],
    providers: [
        ProyectoService,
        ProyectoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesterisProyectoModule {}
