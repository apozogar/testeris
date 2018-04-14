import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Paso } from './paso.model';
import { PasoService } from './paso.service';

@Component({
    selector: 'jhi-paso-detail',
    templateUrl: './paso-detail.component.html'
})
export class PasoDetailComponent implements OnInit, OnDestroy {

    paso: Paso;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pasoService: PasoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPasos();
    }

    load(id) {
        this.pasoService.find(id)
            .subscribe((pasoResponse: HttpResponse<Paso>) => {
                this.paso = pasoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPasos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pasoListModification',
            (response) => this.load(this.paso.id)
        );
    }
}
