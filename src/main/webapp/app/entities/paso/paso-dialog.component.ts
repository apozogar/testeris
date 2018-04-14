import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Paso } from './paso.model';
import { PasoPopupService } from './paso-popup.service';
import { PasoService } from './paso.service';
import { Caso, CasoService } from '../caso';

@Component({
    selector: 'jhi-paso-dialog',
    templateUrl: './paso-dialog.component.html'
})
export class PasoDialogComponent implements OnInit {

    paso: Paso;
    isSaving: boolean;

    casos: Caso[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pasoService: PasoService,
        private casoService: CasoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.casoService.query()
            .subscribe((res: HttpResponse<Caso[]>) => { this.casos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.paso.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pasoService.update(this.paso));
        } else {
            this.subscribeToSaveResponse(
                this.pasoService.create(this.paso));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Paso>>) {
        result.subscribe((res: HttpResponse<Paso>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Paso) {
        this.eventManager.broadcast({ name: 'pasoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCasoById(index: number, item: Caso) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-paso-popup',
    template: ''
})
export class PasoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pasoPopupService: PasoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pasoPopupService
                    .open(PasoDialogComponent as Component, params['id']);
            } else {
                this.pasoPopupService
                    .open(PasoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
