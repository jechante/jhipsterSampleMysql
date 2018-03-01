import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GisServer } from './gis-server.model';
import { GisServerPopupService } from './gis-server-popup.service';
import { GisServerService } from './gis-server.service';

@Component({
    selector: 'jhi-gis-server-delete-dialog',
    templateUrl: './gis-server-delete-dialog.component.html'
})
export class GisServerDeleteDialogComponent {

    gisServer: GisServer;

    constructor(
        private gisServerService: GisServerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gisServerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'gisServerListModification',
                content: 'Deleted an gisServer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gis-server-delete-popup',
    template: ''
})
export class GisServerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gisServerPopupService: GisServerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.gisServerPopupService
                .open(GisServerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
