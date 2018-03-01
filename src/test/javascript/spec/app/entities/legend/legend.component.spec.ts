/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleMysqlTestModule } from '../../../test.module';
import { LegendComponent } from '../../../../../../main/webapp/app/entities/legend/legend.component';
import { LegendService } from '../../../../../../main/webapp/app/entities/legend/legend.service';
import { Legend } from '../../../../../../main/webapp/app/entities/legend/legend.model';

describe('Component Tests', () => {

    describe('Legend Management Component', () => {
        let comp: LegendComponent;
        let fixture: ComponentFixture<LegendComponent>;
        let service: LegendService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleMysqlTestModule],
                declarations: [LegendComponent],
                providers: [
                    LegendService
                ]
            })
            .overrideTemplate(LegendComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LegendComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LegendService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Legend(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.legends[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
