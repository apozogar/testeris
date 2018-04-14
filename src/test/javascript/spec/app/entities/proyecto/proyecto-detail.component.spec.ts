/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TesterisTestModule } from '../../../test.module';
import { ProyectoDetailComponent } from '../../../../../../main/webapp/app/entities/proyecto/proyecto-detail.component';
import { ProyectoService } from '../../../../../../main/webapp/app/entities/proyecto/proyecto.service';
import { Proyecto } from '../../../../../../main/webapp/app/entities/proyecto/proyecto.model';

describe('Component Tests', () => {

    describe('Proyecto Management Detail Component', () => {
        let comp: ProyectoDetailComponent;
        let fixture: ComponentFixture<ProyectoDetailComponent>;
        let service: ProyectoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TesterisTestModule],
                declarations: [ProyectoDetailComponent],
                providers: [
                    ProyectoService
                ]
            })
            .overrideTemplate(ProyectoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProyectoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProyectoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Proyecto(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.proyecto).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
