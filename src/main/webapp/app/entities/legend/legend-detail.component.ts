import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Legend } from './legend.model';
import { LegendService } from './legend.service';

@Component({
    selector: 'jhi-legend-detail',
    templateUrl: './legend-detail.component.html'
})
export class LegendDetailComponent implements OnInit, OnDestroy {

    legend: Legend;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private legendService: LegendService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLegends();
    }

    load(id) {
        this.legendService.find(id)
            .subscribe((legendResponse: HttpResponse<Legend>) => {
                this.legend = legendResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLegends() {
        this.eventSubscriber = this.eventManager.subscribe(
            'legendListModification',
            (response) => this.load(this.legend.id)
        );
    }
}
