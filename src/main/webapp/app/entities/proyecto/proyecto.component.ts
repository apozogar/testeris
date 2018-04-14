import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Proyecto } from './proyecto.model';
import { ProyectoService } from './proyecto.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-proyecto',
    templateUrl: './proyecto.component.html'
})
export class ProyectoComponent implements OnInit, OnDestroy {
proyectos: Proyecto[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private proyectoService: ProyectoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.proyectoService.query().subscribe(
            (res: HttpResponse<Proyecto[]>) => {
                this.proyectos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProyectos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Proyecto) {
        return item.id;
    }
    registerChangeInProyectos() {
        this.eventSubscriber = this.eventManager.subscribe('proyectoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
