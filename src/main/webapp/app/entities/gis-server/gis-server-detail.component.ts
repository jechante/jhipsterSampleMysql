import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { GisServer } from './gis-server.model';
import { GisServerService } from './gis-server.service';

@Component({
    selector: 'jhi-gis-server-detail',
    templateUrl: './gis-server-detail.component.html'
})
export class GisServerDetailComponent implements OnInit, OnDestroy {

    gisServer: GisServer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private gisServerService: GisServerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGisServers();
    }

    load(id) {
        this.gisServerService.find(id)
            .subscribe((gisServerResponse: HttpResponse<GisServer>) => {
                this.gisServer = gisServerResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGisServers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'gisServerListModification',
            (response) => this.load(this.gisServer.id)
        );
    }
}
