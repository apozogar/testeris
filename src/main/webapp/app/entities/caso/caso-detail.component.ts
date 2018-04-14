import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Caso } from './caso.model';
import { CasoService } from './caso.service';

@Component({
    selector: 'jhi-caso-detail',
    templateUrl: './caso-detail.component.html'
})
export class CasoDetailComponent implements OnInit, OnDestroy {

    caso: Caso;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private casoService: CasoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCasos();
    }

    load(id) {
        this.casoService.find(id)
            .subscribe((casoResponse: HttpResponse<Caso>) => {
                this.caso = casoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCasos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'casoListModification',
            (response) => this.load(this.caso.id)
        );
    }
}
