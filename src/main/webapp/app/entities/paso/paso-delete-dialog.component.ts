import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Paso } from './paso.model';
import { PasoPopupService } from './paso-popup.service';
import { PasoService } from './paso.service';

@Component({
    selector: 'jhi-paso-delete-dialog',
    templateUrl: './paso-delete-dialog.component.html'
})
export class PasoDeleteDialogComponent {

    paso: Paso;

    constructor(
        private pasoService: PasoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pasoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pasoListModification',
                content: 'Deleted an paso'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-paso-delete-popup',
    template: ''
})
export class PasoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pasoPopupService: PasoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pasoPopupService
                .open(PasoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
