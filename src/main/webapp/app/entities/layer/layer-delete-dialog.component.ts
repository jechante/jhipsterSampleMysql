import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Layer } from './layer.model';
import { LayerPopupService } from './layer-popup.service';
import { LayerService } from './layer.service';

@Component({
    selector: 'jhi-layer-delete-dialog',
    templateUrl: './layer-delete-dialog.component.html'
})
export class LayerDeleteDialogComponent {

    layer: Layer;

    constructor(
        private layerService: LayerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.layerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'layerListModification',
                content: 'Deleted an layer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-layer-delete-popup',
    template: ''
})
export class LayerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private layerPopupService: LayerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.layerPopupService
                .open(LayerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
