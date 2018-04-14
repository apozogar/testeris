import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Proyecto } from './proyecto.model';
import { ProyectoPopupService } from './proyecto-popup.service';
import { ProyectoService } from './proyecto.service';

@Component({
    selector: 'jhi-proyecto-dialog',
    templateUrl: './proyecto-dialog.component.html'
})
export class ProyectoDialogComponent implements OnInit {

    proyecto: Proyecto;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private proyectoService: ProyectoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.proyecto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.proyectoService.update(this.proyecto));
        } else {
            this.subscribeToSaveResponse(
                this.proyectoService.create(this.proyecto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Proyecto>>) {
        result.subscribe((res: HttpResponse<Proyecto>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Proyecto) {
        this.eventManager.broadcast({ name: 'proyectoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-proyecto-popup',
    template: ''
})
export class ProyectoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private proyectoPopupService: ProyectoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.proyectoPopupService
                    .open(ProyectoDialogComponent as Component, params['id']);
            } else {
                this.proyectoPopupService
                    .open(ProyectoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
