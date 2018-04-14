/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TesterisTestModule } from '../../../test.module';
import { CasoComponent } from '../../../../../../main/webapp/app/entities/caso/caso.component';
import { CasoService } from '../../../../../../main/webapp/app/entities/caso/caso.service';
import { Caso } from '../../../../../../main/webapp/app/entities/caso/caso.model';

describe('Component Tests', () => {

    describe('Caso Management Component', () => {
        let comp: CasoComponent;
        let fixture: ComponentFixture<CasoComponent>;
        let service: CasoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TesterisTestModule],
                declarations: [CasoComponent],
                providers: [
                    CasoService
                ]
            })
            .overrideTemplate(CasoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CasoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CasoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Caso(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.casos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
