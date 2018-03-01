/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleMysqlTestModule } from '../../../test.module';
import { LayerGroupDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/layer-group/layer-group-delete-dialog.component';
import { LayerGroupService } from '../../../../../../main/webapp/app/entities/layer-group/layer-group.service';

describe('Component Tests', () => {

    describe('LayerGroup Management Delete Component', () => {
        let comp: LayerGroupDeleteDialogComponent;
        let fixture: ComponentFixture<LayerGroupDeleteDialogComponent>;
        let service: LayerGroupService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleMysqlTestModule],
                declarations: [LayerGroupDeleteDialogComponent],
                providers: [
                    LayerGroupService
                ]
            })
            .overrideTemplate(LayerGroupDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LayerGroupDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayerGroupService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
