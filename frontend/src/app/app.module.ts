import { NgModule } from '@angular/core';
import { PathLocationStrategy, LocationStrategy } from '@angular/common';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AppLayoutModule } from './admin/layout/app.layout.module';
import { NotfoundComponent } from './admin/pages/notfound/notfound.component';
import { provideClientHydration } from '@angular/platform-browser';

@NgModule({
    declarations: [AppComponent, NotfoundComponent],
    imports: [AppRoutingModule, AppLayoutModule],
    providers: [
        { provide: LocationStrategy, useClass: PathLocationStrategy },
        provideClientHydration(),
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
