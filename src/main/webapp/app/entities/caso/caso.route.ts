import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CasoComponent } from './caso.component';
import { CasoDetailComponent } from './caso-detail.component';
import { CasoPopupComponent } from './caso-dialog.component';
import { CasoDeletePopupComponent } from './caso-delete-dialog.component';

export const casoRoute: Routes = [
    {
        path: 'caso',
        component: CasoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.caso.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'caso/:id',
        component: CasoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.caso.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const casoPopupRoute: Routes = [
    {
        path: 'caso-new',
        component: CasoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.caso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'caso/:id/edit',
        component: CasoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.caso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'caso/:id/delete',
        component: CasoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.caso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
