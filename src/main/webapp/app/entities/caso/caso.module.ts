import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TesterisSharedModule } from '../../shared';
import {
    CasoService,
    CasoPopupService,
    CasoComponent,
    CasoDetailComponent,
    CasoDialogComponent,
    CasoPopupComponent,
    CasoDeletePopupComponent,
    CasoDeleteDialogComponent,
    casoRoute,
    casoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...casoRoute,
    ...casoPopupRoute,
];

@NgModule({
    imports: [
        TesterisSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CasoComponent,
        CasoDetailComponent,
        CasoDialogComponent,
        CasoDeleteDialogComponent,
        CasoPopupComponent,
        CasoDeletePopupComponent,
    ],
    entryComponents: [
        CasoComponent,
        CasoDialogComponent,
        CasoPopupComponent,
        CasoDeleteDialogComponent,
        CasoDeletePopupComponent,
    ],
    providers: [
        CasoService,
        CasoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TesterisCasoModule {}
