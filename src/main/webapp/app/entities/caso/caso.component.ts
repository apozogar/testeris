import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Caso } from './caso.model';
import { CasoService } from './caso.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-caso',
    templateUrl: './caso.component.html'
})
export class CasoComponent implements OnInit, OnDestroy {
casos: Caso[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private casoService: CasoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.casoService.query().subscribe(
            (res: HttpResponse<Caso[]>) => {
                this.casos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCasos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Caso) {
        return item.id;
    }
    registerChangeInCasos() {
        this.eventSubscriber = this.eventManager.subscribe('casoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
