import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PasoComponent } from './paso.component';
import { PasoDetailComponent } from './paso-detail.component';
import { PasoPopupComponent } from './paso-dialog.component';
import { PasoDeletePopupComponent } from './paso-delete-dialog.component';

export const pasoRoute: Routes = [
    {
        path: 'paso',
        component: PasoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.paso.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'paso/:id',
        component: PasoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.paso.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pasoPopupRoute: Routes = [
    {
        path: 'paso-new',
        component: PasoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.paso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paso/:id/edit',
        component: PasoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.paso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paso/:id/delete',
        component: PasoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.paso.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
