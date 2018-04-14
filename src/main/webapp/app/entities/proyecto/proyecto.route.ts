import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ProyectoComponent } from './proyecto.component';
import { ProyectoDetailComponent } from './proyecto-detail.component';
import { ProyectoPopupComponent } from './proyecto-dialog.component';
import { ProyectoDeletePopupComponent } from './proyecto-delete-dialog.component';

export const proyectoRoute: Routes = [
    {
        path: 'proyecto',
        component: ProyectoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.proyecto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'proyecto/:id',
        component: ProyectoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.proyecto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const proyectoPopupRoute: Routes = [
    {
        path: 'proyecto-new',
        component: ProyectoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.proyecto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'proyecto/:id/edit',
        component: ProyectoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.proyecto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'proyecto/:id/delete',
        component: ProyectoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'testerisApp.proyecto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
