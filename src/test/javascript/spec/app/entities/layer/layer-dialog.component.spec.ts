/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleMysqlTestModule } from '../../../test.module';
import { LayerDialogComponent } from '../../../../../../main/webapp/app/entities/layer/layer-dialog.component';
import { LayerService } from '../../../../../../main/webapp/app/entities/layer/layer.service';
import { Layer } from '../../../../../../main/webapp/app/entities/layer/layer.model';
import { GisServerService } from '../../../../../../main/webapp/app/entities/gis-server';
import { LayerGroupService } from '../../../../../../main/webapp/app/entities/layer-group';

describe('Component Tests', () => {

    describe('Layer Management Dialog Component', () => {
        let comp: LayerDialogComponent;
        let fixture: ComponentFixture<LayerDialogComponent>;
        let service: LayerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleMysqlTestModule],
                declarations: [LayerDialogComponent],
                providers: [
                    GisServerService,
                    LayerGroupService,
                    LayerService
                ]
            })
            .overrideTemplate(LayerDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LayerDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayerService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Layer(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.layer = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'layerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Layer();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.layer = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'layerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
