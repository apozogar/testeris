/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TesterisTestModule } from '../../../test.module';
import { PasoDetailComponent } from '../../../../../../main/webapp/app/entities/paso/paso-detail.component';
import { PasoService } from '../../../../../../main/webapp/app/entities/paso/paso.service';
import { Paso } from '../../../../../../main/webapp/app/entities/paso/paso.model';

describe('Component Tests', () => {

    describe('Paso Management Detail Component', () => {
        let comp: PasoDetailComponent;
        let fixture: ComponentFixture<PasoDetailComponent>;
        let service: PasoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TesterisTestModule],
                declarations: [PasoDetailComponent],
                providers: [
                    PasoService
                ]
            })
            .overrideTemplate(PasoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PasoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PasoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Paso(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.paso).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
