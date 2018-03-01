import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LayerGroup } from './layer-group.model';
import { LayerGroupPopupService } from './layer-group-popup.service';
import { LayerGroupService } from './layer-group.service';

@Component({
    selector: 'jhi-layer-group-dialog',
    templateUrl: './layer-group-dialog.component.html'
})
export class LayerGroupDialogComponent implements OnInit {

    layerGroup: LayerGroup;
    isSaving: boolean;

    layergroups: LayerGroup[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private layerGroupService: LayerGroupService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.layerGroupService.query()
            .subscribe((res: HttpResponse<LayerGroup[]>) => { this.layergroups = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.layerGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.layerGroupService.update(this.layerGroup));
        } else {
            this.subscribeToSaveResponse(
                this.layerGroupService.create(this.layerGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LayerGroup>>) {
        result.subscribe((res: HttpResponse<LayerGroup>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LayerGroup) {
        this.eventManager.broadcast({ name: 'layerGroupListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLayerGroupById(index: number, item: LayerGroup) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-layer-group-popup',
    template: ''
})
export class LayerGroupPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private layerGroupPopupService: LayerGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.layerGroupPopupService
                    .open(LayerGroupDialogComponent as Component, params['id']);
            } else {
                this.layerGroupPopupService
                    .open(LayerGroupDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
