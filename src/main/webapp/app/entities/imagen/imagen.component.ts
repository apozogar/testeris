import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Imagen } from './imagen.model';
import { ImagenService } from './imagen.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-imagen',
    templateUrl: './imagen.component.html'
})
export class ImagenComponent implements OnInit, OnDestroy {
imagens: Imagen[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private imagenService: ImagenService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.imagenService.query().subscribe(
            (res: HttpResponse<Imagen[]>) => {
                this.imagens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInImagens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Imagen) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInImagens() {
        this.eventSubscriber = this.eventManager.subscribe('imagenListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
