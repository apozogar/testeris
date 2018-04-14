import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Caso } from './caso.model';
import { CasoPopupService } from './caso-popup.service';
import { CasoService } from './caso.service';

@Component({
    selector: 'jhi-caso-delete-dialog',
    templateUrl: './caso-delete-dialog.component.html'
})
export class CasoDeleteDialogComponent {

    caso: Caso;

    constructor(
        private casoService: CasoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.casoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'casoListModification',
                content: 'Deleted an caso'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-caso-delete-popup',
    template: ''
})
export class CasoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private casoPopupService: CasoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.casoPopupService
                .open(CasoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
