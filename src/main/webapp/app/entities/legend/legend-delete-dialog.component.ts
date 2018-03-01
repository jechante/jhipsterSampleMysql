import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Legend } from './legend.model';
import { LegendPopupService } from './legend-popup.service';
import { LegendService } from './legend.service';

@Component({
    selector: 'jhi-legend-delete-dialog',
    templateUrl: './legend-delete-dialog.component.html'
})
export class LegendDeleteDialogComponent {

    legend: Legend;

    constructor(
        private legendService: LegendService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.legendService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'legendListModification',
                content: 'Deleted an legend'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-legend-delete-popup',
    template: ''
})
export class LegendDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private legendPopupService: LegendPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.legendPopupService
                .open(LegendDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
