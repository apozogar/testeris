import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    TesterisSharedLibsModule,
    TesterisSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    Principal,
    HasAnyAuthorityDirective,
} from './';

@NgModule({
    imports: [
        TesterisSharedLibsModule,
        TesterisSharedCommonModule
    ],
    declarations: [
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe
    ],
    exports: [
        TesterisSharedCommonModule,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class TesterisSharedModule {}
