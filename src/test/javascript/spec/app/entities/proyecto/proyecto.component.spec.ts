/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TesterisTestModule } from '../../../test.module';
import { ProyectoComponent } from '../../../../../../main/webapp/app/entities/proyecto/proyecto.component';
import { ProyectoService } from '../../../../../../main/webapp/app/entities/proyecto/proyecto.service';
import { Proyecto } from '../../../../../../main/webapp/app/entities/proyecto/proyecto.model';

describe('Component Tests', () => {

    describe('Proyecto Management Component', () => {
        let comp: ProyectoComponent;
        let fixture: ComponentFixture<ProyectoComponent>;
        let service: ProyectoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TesterisTestModule],
                declarations: [ProyectoComponent],
                providers: [
                    ProyectoService
                ]
            })
            .overrideTemplate(ProyectoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProyectoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProyectoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Proyecto(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.proyectos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
