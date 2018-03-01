import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LayerGroup } from './layer-group.model';
import { LayerGroupService } from './layer-group.service';

@Component({
    selector: 'jhi-layer-group-detail',
    templateUrl: './layer-group-detail.component.html'
})
export class LayerGroupDetailComponent implements OnInit, OnDestroy {

    layerGroup: LayerGroup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private layerGroupService: LayerGroupService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLayerGroups();
    }

    load(id) {
        this.layerGroupService.find(id)
            .subscribe((layerGroupResponse: HttpResponse<LayerGroup>) => {
                this.layerGroup = layerGroupResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLayerGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'layerGroupListModification',
            (response) => this.load(this.layerGroup.id)
        );
    }
}
