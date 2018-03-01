import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { LayerGroup } from './layer-group.model';
import { LayerGroupService } from './layer-group.service';

@Injectable()
export class LayerGroupPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private layerGroupService: LayerGroupService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.layerGroupService.find(id)
                    .subscribe((layerGroupResponse: HttpResponse<LayerGroup>) => {
                        const layerGroup: LayerGroup = layerGroupResponse.body;
                        this.ngbModalRef = this.layerGroupModalRef(component, layerGroup);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.layerGroupModalRef(component, new LayerGroup());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    layerGroupModalRef(component: Component, layerGroup: LayerGroup): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.layerGroup = layerGroup;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
