import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ImagenComponent } from './imagen.component';
import { ImagenDetailComponent } from './imagen-detail.component';
import { ImagenPopupComponent } from './imagen-dialog.component';
import { ImagenDeletePopupComponent } from './imagen-delete-dialog.component';

export const imagenRoute: Routes = [
    {
        path: 'imagen',
        component: ImagenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.imagen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'imagen/:id',
        component: ImagenDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.imagen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imagenPopupRoute: Routes = [
    {
        path: 'imagen-new',
        component: ImagenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.imagen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'imagen/:id/edit',
        component: ImagenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.imagen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'imagen/:id/delete',
        component: ImagenDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.imagen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
