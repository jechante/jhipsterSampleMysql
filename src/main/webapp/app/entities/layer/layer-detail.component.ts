import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Layer } from './layer.model';
import { LayerService } from './layer.service';

@Component({
    selector: 'jhi-layer-detail',
    templateUrl: './layer-detail.component.html'
})
export class LayerDetailComponent implements OnInit, OnDestroy {

    layer: Layer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private layerService: LayerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLayers();
    }

    load(id) {
        this.layerService.find(id)
            .subscribe((layerResponse: HttpResponse<Layer>) => {
                this.layer = layerResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLayers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'layerListModification',
            (response) => this.load(this.layer.id)
        );
    }
}
