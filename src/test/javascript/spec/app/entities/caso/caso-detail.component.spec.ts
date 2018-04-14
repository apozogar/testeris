/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TesterisTestModule } from '../../../test.module';
import { CasoDetailComponent } from '../../../../../../main/webapp/app/entities/caso/caso-detail.component';
import { CasoService } from '../../../../../../main/webapp/app/entities/caso/caso.service';
import { Caso } from '../../../../../../main/webapp/app/entities/caso/caso.model';

describe('Component Tests', () => {

    describe('Caso Management Detail Component', () => {
        let comp: CasoDetailComponent;
        let fixture: ComponentFixture<CasoDetailComponent>;
        let service: CasoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TesterisTestModule],
                declarations: [CasoDetailComponent],
                providers: [
                    CasoService
                ]
            })
            .overrideTemplate(CasoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CasoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CasoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Caso(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.caso).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
