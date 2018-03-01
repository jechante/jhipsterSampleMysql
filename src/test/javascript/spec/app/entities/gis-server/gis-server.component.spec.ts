/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleMysqlTestModule } from '../../../test.module';
import { GisServerComponent } from '../../../../../../main/webapp/app/entities/gis-server/gis-server.component';
import { GisServerService } from '../../../../../../main/webapp/app/entities/gis-server/gis-server.service';
import { GisServer } from '../../../../../../main/webapp/app/entities/gis-server/gis-server.model';

describe('Component Tests', () => {

    describe('GisServer Management Component', () => {
        let comp: GisServerComponent;
        let fixture: ComponentFixture<GisServerComponent>;
        let service: GisServerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleMysqlTestModule],
                declarations: [GisServerComponent],
                providers: [
                    GisServerService
                ]
            })
            .overrideTemplate(GisServerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GisServerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GisServerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new GisServer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.gisServers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
