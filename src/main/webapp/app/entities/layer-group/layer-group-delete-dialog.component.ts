import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LayerGroup } from './layer-group.model';
import { LayerGroupPopupService } from './layer-group-popup.service';
import { LayerGroupService } from './layer-group.service';

@Component({
    selector: 'jhi-layer-group-delete-dialog',
    templateUrl: './layer-group-delete-dialog.component.html'
})
export class LayerGroupDeleteDialogComponent {

    layerGroup: LayerGroup;

    constructor(
        private layerGroupService: LayerGroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.layerGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'layerGroupListModification',
                content: 'Deleted an layerGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-layer-group-delete-popup',
    template: ''
})
export class LayerGroupDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private layerGroupPopupService: LayerGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.layerGroupPopupService
                .open(LayerGroupDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
