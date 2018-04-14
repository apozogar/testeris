/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TesterisTestModule } from '../../../test.module';
import { PasoComponent } from '../../../../../../main/webapp/app/entities/paso/paso.component';
import { PasoService } from '../../../../../../main/webapp/app/entities/paso/paso.service';
import { Paso } from '../../../../../../main/webapp/app/entities/paso/paso.model';

describe('Component Tests', () => {

    describe('Paso Management Component', () => {
        let comp: PasoComponent;
        let fixture: ComponentFixture<PasoComponent>;
        let service: PasoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TesterisTestModule],
                declarations: [PasoComponent],
                providers: [
                    PasoService
                ]
            })
            .overrideTemplate(PasoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PasoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PasoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Paso(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pasos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
