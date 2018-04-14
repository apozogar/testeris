import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Proyecto } from './proyecto.model';
import { ProyectoService } from './proyecto.service';

@Component({
    selector: 'jhi-proyecto-detail',
    templateUrl: './proyecto-detail.component.html'
})
export class ProyectoDetailComponent implements OnInit, OnDestroy {

    proyecto: Proyecto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private proyectoService: ProyectoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProyectos();
    }

    load(id) {
        this.proyectoService.find(id)
            .subscribe((proyectoResponse: HttpResponse<Proyecto>) => {
                this.proyecto = proyectoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProyectos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'proyectoListModification',
            (response) => this.load(this.proyecto.id)
        );
    }
}
