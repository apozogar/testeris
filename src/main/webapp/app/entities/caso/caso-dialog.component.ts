import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Caso } from './caso.model';
import { CasoPopupService } from './caso-popup.service';
import { CasoService } from './caso.service';
import { Proyecto, ProyectoService } from '../proyecto';

@Component({
    selector: 'jhi-caso-dialog',
    templateUrl: './caso-dialog.component.html'
})
export class CasoDialogComponent implements OnInit {

    caso: Caso;
    isSaving: boolean;

    proyectos: Proyecto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private casoService: CasoService,
        private proyectoService: ProyectoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.proyectoService.query()
            .subscribe((res: HttpResponse<Proyecto[]>) => { this.proyectos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.caso.id !== undefined) {
            this.subscribeToSaveResponse(
                this.casoService.update(this.caso));
        } else {
            this.subscribeToSaveResponse(
                this.casoService.create(this.caso));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Caso>>) {
        result.subscribe((res: HttpResponse<Caso>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Caso) {
        this.eventManager.broadcast({ name: 'casoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProyectoById(index: number, item: Proyecto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-caso-popup',
    template: ''
})
export class CasoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private casoPopupService: CasoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.casoPopupService
                    .open(CasoDialogComponent as Component, params['id']);
            } else {
                this.casoPopupService
                    .open(CasoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
