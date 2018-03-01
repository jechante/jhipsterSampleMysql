/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleMysqlTestModule } from '../../../test.module';
import { LayerComponent } from '../../../../../../main/webapp/app/entities/layer/layer.component';
import { LayerService } from '../../../../../../main/webapp/app/entities/layer/layer.service';
import { Layer } from '../../../../../../main/webapp/app/entities/layer/layer.model';

describe('Component Tests', () => {

    describe('Layer Management Component', () => {
        let comp: LayerComponent;
        let fixture: ComponentFixture<LayerComponent>;
        let service: LayerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleMysqlTestModule],
                declarations: [LayerComponent],
                providers: [
                    LayerService
                ]
            })
            .overrideTemplate(LayerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LayerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Layer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.layers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
