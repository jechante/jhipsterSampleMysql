import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Legend } from './legend.model';
import { LegendPopupService } from './legend-popup.service';
import { LegendService } from './legend.service';
import { Layer, LayerService } from '../layer';

@Component({
    selector: 'jhi-legend-dialog',
    templateUrl: './legend-dialog.component.html'
})
export class LegendDialogComponent implements OnInit {

    legend: Legend;
    isSaving: boolean;

    layers: Layer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private legendService: LegendService,
        private layerService: LayerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.layerService.query()
            .subscribe((res: HttpResponse<Layer[]>) => { this.layers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.legend.id !== undefined) {
            this.subscribeToSaveResponse(
                this.legendService.update(this.legend));
        } else {
            this.subscribeToSaveResponse(
                this.legendService.create(this.legend));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Legend>>) {
        result.subscribe((res: HttpResponse<Legend>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Legend) {
        this.eventManager.broadcast({ name: 'legendListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLayerById(index: number, item: Layer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-legend-popup',
    template: ''
})
export class LegendPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private legendPopupService: LegendPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.legendPopupService
                    .open(LegendDialogComponent as Component, params['id']);
            } else {
                this.legendPopupService
                    .open(LegendDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
