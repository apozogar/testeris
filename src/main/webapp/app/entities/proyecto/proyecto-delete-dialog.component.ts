import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Proyecto } from './proyecto.model';
import { ProyectoPopupService } from './proyecto-popup.service';
import { ProyectoService } from './proyecto.service';

@Component({
    selector: 'jhi-proyecto-delete-dialog',
    templateUrl: './proyecto-delete-dialog.component.html'
})
export class ProyectoDeleteDialogComponent {

    proyecto: Proyecto;

    constructor(
        private proyectoService: ProyectoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.proyectoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'proyectoListModification',
                content: 'Deleted an proyecto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-proyecto-delete-popup',
    template: ''
})
export class ProyectoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private proyectoPopupService: ProyectoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.proyectoPopupService
                .open(ProyectoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
