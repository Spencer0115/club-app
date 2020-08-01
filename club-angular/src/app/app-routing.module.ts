import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TeamsComponent} from './components/teams/teams.component';
import {LoginComponent} from './components/login/login.component';
import {SignUpComponent} from './components/sign-up/sign-up.component';
import {SuccessComponent} from './components/success/success.component';
import { UserComponent } from './components/user/user.component';
import { EventsComponent } from './components/events/events.component';
import { TeamComponent } from './components/team/team.component';
import { PlayerComponent } from './components/player/player.component';
import { HomeComponent } from './components/home/home.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent},
  { path: 'signUp', component: SignUpComponent},
  { path: 'success', component: SuccessComponent},
  { path: 'user', 
    component: UserComponent,
    children: [
    { path: 'home', component: HomeComponent },
    { path: 'club_team', component: TeamsComponent },
    { path: 'team_club/:id', component: TeamComponent },
    { path: 'club_player/:id', component: PlayerComponent },
    { path: 'club_event', component: EventsComponent },
    { path: 'player_team', component: TeamComponent },
    { path: 'player_info', component: PlayerComponent }
    ]
}
  
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
