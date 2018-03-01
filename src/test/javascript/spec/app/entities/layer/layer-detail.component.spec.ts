/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleMysqlTestModule } from '../../../test.module';
import { LayerDetailComponent } from '../../../../../../main/webapp/app/entities/layer/layer-detail.component';
import { LayerService } from '../../../../../../main/webapp/app/entities/layer/layer.service';
import { Layer } from '../../../../../../main/webapp/app/entities/layer/layer.model';

describe('Component Tests', () => {

    describe('Layer Management Detail Component', () => {
        let comp: LayerDetailComponent;
        let fixture: ComponentFixture<LayerDetailComponent>;
        let service: LayerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleMysqlTestModule],
                declarations: [LayerDetailComponent],
                providers: [
                    LayerService
                ]
            })
            .overrideTemplate(LayerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LayerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Layer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.layer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
