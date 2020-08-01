import { BrowserModule,Title} from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PlayerComponent } from './components/player/player.component';
import { MessagesComponent } from './components/messages/messages.component';
import { TeamsComponent } from './components/teams/teams.component';
import { TeamComponent } from './components/team/team.component';
import { EventsComponent } from './components/events/events.component';
import { HttpClientModule } from '@angular/common/http';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { LoginComponent } from './components/login/login.component';
import { UserComponent } from './components/user/user.component';
import { SuccessComponent } from './components/success/success.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { HomeComponent } from './components/home/home.component';
@NgModule({
  declarations: [
    AppComponent,
    PlayerComponent,
    MessagesComponent,
    TeamsComponent,
    EventsComponent,
    SignUpComponent,
    LoginComponent,
    UserComponent,
    SuccessComponent,
    TeamComponent,
    HomeComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [
    Title
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
