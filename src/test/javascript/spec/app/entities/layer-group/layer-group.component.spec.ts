/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleMysqlTestModule } from '../../../test.module';
import { LayerGroupComponent } from '../../../../../../main/webapp/app/entities/layer-group/layer-group.component';
import { LayerGroupService } from '../../../../../../main/webapp/app/entities/layer-group/layer-group.service';
import { LayerGroup } from '../../../../../../main/webapp/app/entities/layer-group/layer-group.model';

describe('Component Tests', () => {

    describe('LayerGroup Management Component', () => {
        let comp: LayerGroupComponent;
        let fixture: ComponentFixture<LayerGroupComponent>;
        let service: LayerGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleMysqlTestModule],
                declarations: [LayerGroupComponent],
                providers: [
                    LayerGroupService
                ]
            })
            .overrideTemplate(LayerGroupComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LayerGroupComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayerGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LayerGroup(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.layerGroups[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
