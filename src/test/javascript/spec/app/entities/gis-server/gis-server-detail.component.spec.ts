/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JhipsterSampleMysqlTestModule } from '../../../test.module';
import { GisServerDetailComponent } from '../../../../../../main/webapp/app/entities/gis-server/gis-server-detail.component';
import { GisServerService } from '../../../../../../main/webapp/app/entities/gis-server/gis-server.service';
import { GisServer } from '../../../../../../main/webapp/app/entities/gis-server/gis-server.model';

describe('Component Tests', () => {

    describe('GisServer Management Detail Component', () => {
        let comp: GisServerDetailComponent;
        let fixture: ComponentFixture<GisServerDetailComponent>;
        let service: GisServerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleMysqlTestModule],
                declarations: [GisServerDetailComponent],
                providers: [
                    GisServerService
                ]
            })
            .overrideTemplate(GisServerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GisServerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GisServerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new GisServer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.gisServer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
