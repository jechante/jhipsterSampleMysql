import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GisServer } from './gis-server.model';
import { GisServerPopupService } from './gis-server-popup.service';
import { GisServerService } from './gis-server.service';

@Component({
    selector: 'jhi-gis-server-dialog',
    templateUrl: './gis-server-dialog.component.html'
})
export class GisServerDialogComponent implements OnInit {

    gisServer: GisServer;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private gisServerService: GisServerService,
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
        if (this.gisServer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.gisServerService.update(this.gisServer));
        } else {
            this.subscribeToSaveResponse(
                this.gisServerService.create(this.gisServer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<GisServer>>) {
        result.subscribe((res: HttpResponse<GisServer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: GisServer) {
        this.eventManager.broadcast({ name: 'gisServerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-gis-server-popup',
    template: ''
})
export class GisServerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gisServerPopupService: GisServerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.gisServerPopupService
                    .open(GisServerDialogComponent as Component, params['id']);
            } else {
                this.gisServerPopupService
                    .open(GisServerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
