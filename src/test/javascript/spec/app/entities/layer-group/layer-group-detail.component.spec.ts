/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleMysqlTestModule } from '../../../test.module';
import { LayerGroupDetailComponent } from '../../../../../../main/webapp/app/entities/layer-group/layer-group-detail.component';
import { LayerGroupService } from '../../../../../../main/webapp/app/entities/layer-group/layer-group.service';
import { LayerGroup } from '../../../../../../main/webapp/app/entities/layer-group/layer-group.model';

describe('Component Tests', () => {

    describe('LayerGroup Management Detail Component', () => {
        let comp: LayerGroupDetailComponent;
        let fixture: ComponentFixture<LayerGroupDetailComponent>;
        let service: LayerGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleMysqlTestModule],
                declarations: [LayerGroupDetailComponent],
                providers: [
                    LayerGroupService
                ]
            })
            .overrideTemplate(LayerGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LayerGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayerGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LayerGroup(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.layerGroup).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
