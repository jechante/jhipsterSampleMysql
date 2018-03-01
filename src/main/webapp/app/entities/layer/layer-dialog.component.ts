import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Layer } from './layer.model';
import { LayerPopupService } from './layer-popup.service';
import { LayerService } from './layer.service';
import { GisServer, GisServerService } from '../gis-server';
import { LayerGroup, LayerGroupService } from '../layer-group';

@Component({
    selector: 'jhi-layer-dialog',
    templateUrl: './layer-dialog.component.html'
})
export class LayerDialogComponent implements OnInit {

    layer: Layer;
    isSaving: boolean;

    gisservers: GisServer[];

    layergroups: LayerGroup[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private layerService: LayerService,
        private gisServerService: GisServerService,
        private layerGroupService: LayerGroupService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.gisServerService.query()
            .subscribe((res: HttpResponse<GisServer[]>) => { this.gisservers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.layerGroupService.query()
            .subscribe((res: HttpResponse<LayerGroup[]>) => { this.layergroups = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.layer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.layerService.update(this.layer));
        } else {
            this.subscribeToSaveResponse(
                this.layerService.create(this.layer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Layer>>) {
        result.subscribe((res: HttpResponse<Layer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Layer) {
        this.eventManager.broadcast({ name: 'layerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackGisServerById(index: number, item: GisServer) {
        return item.id;
    }

    trackLayerGroupById(index: number, item: LayerGroup) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-layer-popup',
    template: ''
})
export class LayerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private layerPopupService: LayerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.layerPopupService
                    .open(LayerDialogComponent as Component, params['id']);
            } else {
                this.layerPopupService
                    .open(LayerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
