import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Paso } from './paso.model';
import { PasoService } from './paso.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-paso',
    templateUrl: './paso.component.html'
})
export class PasoComponent implements OnInit, OnDestroy {
pasos: Paso[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pasoService: PasoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pasoService.query().subscribe(
            (res: HttpResponse<Paso[]>) => {
                this.pasos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPasos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Paso) {
        return item.id;
    }
    registerChangeInPasos() {
        this.eventSubscriber = this.eventManager.subscribe('pasoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
